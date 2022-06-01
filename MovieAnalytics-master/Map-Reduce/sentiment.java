package hadoop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.VLongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class sentiment {

    public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {

        private HashMap<String, Integer> pos;
        private HashMap<String, Integer> neg;
        private porterstemmer stemmer;
        static String productID = "";

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String s[] = line.split(" ");
            if (s[0].equals("product/productId:")) {
                productID = s[1];
            }
            if (s[0].equals("review/text:")) {

               /* String token = null;
                int score = 0;
                StringTokenizer st = new StringTokenizer(s[1]);
                while (st.hasMoreTokens()) {
                    token = st.nextToken();
                    token = stemmer.stripAffixes(token);
                    if (pos.containsKey(token)) {
                        score++;
                    }
                    if (neg.containsKey(token)) {
                        score--;
                    }
                }
                int classlabel;
                if (score > 0) {
                    classlabel = 0;
                } else {
                    classlabel = 1;
                }  */              
                context.write(new Text(productID), new IntWritable(1));
                 
            }
        }

        public void setup(Context context) throws IOException {
            Configuration conf = context.getConfiguration();
            stemmer = new porterstemmer();
            String location = "/usr/local/hadoop/pos.txt";
            if (location != null) {
                BufferedReader br = null;
                FSDataInputStream fis = null;
                try {
                    FileSystem fs = FileSystem.get(conf);
                    Path path = new Path(location);
                    if (fs.exists(path)) {
                        pos = new HashMap<String, Integer>();
                        fis = fs.open(path);
                        br = new BufferedReader(new InputStreamReader(fis));
                        String line = null;
                        while ((line = br.readLine()) != null && line.trim().length() > 0) {
                            line = stemmer.stripAffixes(line);
                            if (!pos.containsKey(line)) {
                                pos.put(line, 1);
                            }

                        }
                    }
                } catch (IOException e) {

                } finally {
                    fis.close();
                    IOUtils.closeStream(br);
                }
            }
            location = "/usr/local/hadoop/neg.txt";
            if (location != null) {
                BufferedReader br = null;
                FSDataInputStream fis = null;
                try {
                    FileSystem fs = FileSystem.get(conf);
                    Path path = new Path(location);
                    if (fs.exists(path)) {
                        neg = new HashMap<String, Integer>();
                        fis = fs.open(path);
                        br = new BufferedReader(new InputStreamReader(fis));
                        String line = null;
                        while ((line = br.readLine()) != null && line.trim().length() > 0) {
                            line = stemmer.stripAffixes(line);
                            if (!neg.containsKey(line)) {
                                neg.put(line, -1);
                            }
                        }
                    }
                } catch (IOException e) {

                } finally {
                    fis.close();
                    IOUtils.closeStream(br);
                }
            }
        }
    }

    public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {

        public void reduce(Text key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {
            int addition = 0;
            for (IntWritable val : values) {
                addition += val.get();
            }
            context.write(key, new IntWritable(addition));
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        @SuppressWarnings("deprecation")
        Job job = new Job(conf, "mat");

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }
}
