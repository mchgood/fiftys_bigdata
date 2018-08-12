package cn.fiftys.map.mapjoin;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.*;
import java.util.HashMap;

public class MapJoinMapper extends Mapper<LongWritable, Text, Text, NullWritable> {


    public static class OrderAndProduct implements WritableComparable<OrderAndProduct> {

        private String oid;

        private String date;

        private String pid;

        private String pname;

        private String price;

        private String counts;

        @Override
        public int compareTo(OrderAndProduct o) {
            return this.oid.compareTo(oid);
        }

        @Override
        public void write(DataOutput out) throws IOException {
            out.writeUTF(oid);
            out.writeUTF(date);
            out.writeUTF(pid);
            out.writeUTF(pname);
            out.writeUTF(price);
            out.writeUTF(counts);
        }

        @Override
        public void readFields(DataInput in) throws IOException {
            this.oid = in.readUTF();
            this.date = in.readUTF();
            this.pid = in.readUTF();
            this.pname = in.readUTF();
            this.price = in.readUTF();
            this.counts = in.readUTF();
        }
    }

    private HashMap<String, OrderAndProduct> productMap = new HashMap<>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //首先换取到商品订单
        String path = context.getCacheFiles()[0].getPath().substring(1);
        //读取商品订单中的数据
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            String[] arr = line.trim().split(",");
            OrderAndProduct oap = new OrderAndProduct();
            oap.pid = arr[0];
            oap.pname = arr[1];
            oap.price = arr[2];
            productMap.put(oap.pid, oap);
        }
        br.close();
    }

    Text   k = new Text();
    NullWritable v = NullWritable.get();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] arr = value.toString().trim().split(",");
        OrderAndProduct oap = productMap.get(arr[2]);

        StringBuilder sb = new StringBuilder();
        sb.append(arr[0]).append(",").append(arr[1]).append(",").append(arr[2]).append(",").append(oap.pname).append(",").append(oap.price).append(",").append(arr[3]);
        k.set(sb.toString());
        context.write(k, v);
    }


}
