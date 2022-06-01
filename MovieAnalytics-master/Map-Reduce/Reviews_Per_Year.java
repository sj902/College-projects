package hadoop;

import java.util.*;

import java.io.IOException;
import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class Reviews_Per_Year {

    static String movieId = "";
    static String Date = "";
    static String key_out = "";
    //Mapper class 

    public static class E_EMapper extends MapReduceBase implements
            Mapper<LongWritable,/*Input key Type */ Text, /*Input value Type*/ Text, /*Output key Type*/ IntWritable> /*Output value Type*/ {

        //Map function 
        public void map(LongWritable key, Text value,
                OutputCollector<Text, IntWritable> output,
                Reporter reporter) throws IOException {
            String line = value.toString();
            String s[] = line.split(" ");
            if (s[0].equals("product/productId:")) {
                movieId = s[1];
            } else if (s[0].equals("review/score:")) {
                output.collect(new Text(key_out), new IntWritable(1));
            } else if (s[0].equals("review/time:")) {
                String Date = new java.text.SimpleDateFormat("yyyy").format(new java.util.Date(Long.parseLong(s[1]) * 1000));
                key_out = movieId + "&" + Date;
            }

        }
    }

    //Reducer class 
    public static class E_EReduce extends MapReduceBase implements
            Reducer< Text, IntWritable, Text, IntWritable> {

        //Reduce function 
        public void reduce(Text key, Iterator<IntWritable> values,
                OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {

            int z = 0;
            while (values.hasNext()) {
                z += values.next().get();
            }
            output.collect(key, new IntWritable(z));
        }
    }

    //Main function 
    public static void main(String args[]) throws Exception {
        JobConf conf = new JobConf(Reviews_Per_Year.class);

        conf.setJobName("Reviews_Per_Year");
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
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
