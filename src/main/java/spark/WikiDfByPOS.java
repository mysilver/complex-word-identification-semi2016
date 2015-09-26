package spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class WikiDfByPOS {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("WikiDfByPOS");
        JavaSparkContext context = new JavaSparkContext(conf);
        JavaRDD<String> input = context.textFile("hdfs://ilab-1/user/indexer/analysis/wiki/enwiki-latest-pages-articles.xml.bz2_sentence");

        JavaPairRDD<String, Integer> t_pos = input.flatMapToPair(new PairFlatMapFunction<String, String, Integer>() {
            @Override
            public Iterable<Tuple2<String, Integer>> call(String s) throws Exception {
                System.out.println(s);
                String[] split = s.split("\t");
                String[] lemma = split[2].split(" ");
                String[] poss = split[3].split(" ");

                List<Tuple2<String, Integer>> result = new ArrayList<>(lemma.length);
                for (int i = 0; i < lemma.length; i++) {
                    result.add(new Tuple2<>(lemma[i].trim() + "\t" + poss[i], 1));
                }

                return result;
            }
        });

        JavaPairRDD<String, Integer> t_pos_ferq = t_pos.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) throws Exception {
                return integer+integer2;
            }
        });

        t_pos_ferq.mapToPair(new PairFunction<Tuple2<String, Integer>, Integer, String>() {
            @Override
            public Tuple2<Integer, String> call(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
                return new Tuple2<Integer, String>(stringIntegerTuple2._2(), stringIntegerTuple2._1());
            }
        }).sortByKey(false).coalesce(1).saveAsTextFile("/home/mohammad-ali/Temp/POS_TERM_FREQ");

        /*for (Tuple2<Integer, String> integerStringTuple2 : result) {
            System.out.println(integerStringTuple2);
        }*/

    }
}
