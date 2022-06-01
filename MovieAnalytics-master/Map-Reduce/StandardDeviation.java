package hadoop;

import java.util.*;

import java.io.IOException;
import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class StandardDeviation {

    //static String userId = "";
    static double total = 0;
    static double totalDataPoints = 0;
    //Mapper class 
    static double square = 0;

    public static class E_EMapper extends MapReduceBase implements
            Mapper<Text,/*Input key Type */ Text, /*Input value Type*/ Text, /*Output key Type*/ DoubleWritable> /*Output value Type*/ {

        //Map function 
        public void map(Text key, Text value,
                OutputCollector<Text, DoubleWritable> output,
                Reporter reporter) throws IOException {           
            
            if (!(key.toString().charAt(0) + "").equals("#")) {
                output.collect(new Text(key.toString()), new DoubleWritable(Double.parseDouble(value.toString())));
            }

        }
    }

    //Reducer class 
    public static class E_EReduce extends MapReduceBase implements
            Reducer< Text, DoubleWritable, Text, DoubleWritable> {

        //Reduce function 
        public void reduce(Text key, Iterator<DoubleWritable> values,
                OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException {

            while (values.hasNext()) {
                square += Math.pow((values.next().get() - 4.1), 2);
                totalDataPoints++;
            }
            if (totalDataPoints > 1) {
                output.collect(key, new DoubleWritable(Math.sqrt((square / (totalDataPoints - 1)))));
            }
        }
    }

    //Main function 
    public static void main(String args[]) throws Exception {
        JobConf conf = new JobConf(StandardDeviation.class);

        conf.setJobName("Average_Score");
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(DoubleWritable.class);
        conf.setMapperClass(E_EMapper.class);
        // conf.setCombinerClass(E_EReduce.class);
        conf.setReducerClass(E_EReduce.class);
        conf.setInputFormat(KeyValueTextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        JobClient.runJob(conf);
    }
}
