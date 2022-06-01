package hadoop;

import java.util.*;

import java.io.IOException;
import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class Hadoop {

    static String userId = "";
    //Mapper class 

    public static class E_EMapper extends MapReduceBase implements
            Mapper<LongWritable,/*Input key Type */ Text, /*Input value Type*/ Text, /*Output key Type*/ DoubleWritable> /*Output value Type*/ {

        //Map function 
        public void map(LongWritable key, Text value,
                OutputCollector<Text, DoubleWritable> output,
                Reporter reporter) throws IOException {
            String line = value.toString();
            String s[] = line.split(" ");
            if (s[0].equals("review/userId:")) {
                userId = s[1];
            } else if (s[0].equals("review/score:")) {
                output.collect(new Text(userId), new DoubleWritable(Double.parseDouble(s[1])));
            }

        }
    }

    //Reducer class 
    public static class E_EReduce extends MapReduceBase implements
            Reducer< Text, DoubleWritable, Text, DoubleWritable> {

        //Reduce function 
        public void reduce(Text key, Iterator<DoubleWritable> values,
                OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException {
            int maxavg = 30;
            int val = Integer.MIN_VALUE;
            double avg = 0;
            int z = 0;
            while (values.hasNext()) {
                avg += values.next().get();
                z++;
            }
            output.collect(key, new DoubleWritable((((double) avg) / z)));
        }
    }

    //Main function 
    public static void main(String args[]) throws Exception {
        JobConf conf = new JobConf(Hadoop.class);

        conf.setJobName("Average_Score");
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(DoubleWritable.class);
        conf.setMapperClass(E_EMapper.class);
        conf.setCombinerClass(E_EReduce.class);
        conf.setReducerClass(E_EReduce.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        JobClient.runJob(conf);
    }
}
