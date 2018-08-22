package cn.fiftys.scala.actor

import scala.actors.Actor
import scala.io.Source

/**
  * 需求:用 actor 并发编程写一个单机版的 WordCount，将多个文件作为输入，计算完成后将多个任务汇总，得到最终的结果。
  * 大致的思想步骤：
  * 1、通过 loop +react 方式去不断的接受消息
  * 2、利用 case class 样例类去匹配对应的操作
  * 3、其中 scala 中提供了文件读取的接口 Source,通过调用其 fromFile 方法去获取文件内容
  * 4、将每个文件的单词数量进行局部汇总，存放在一个 ListBuffer 中
  * 5、最后将 ListBuffer 中的结果进行全局汇总。
  */
case class SubmitTask(fileName: String)

class StandAloneTask extends Actor {
  override def act(): Unit = {
    loop {
      react {
        case SubmitTask(fileName) => {
          //1,通过Scala中提供文本接口获取到文本内容,并通过mkString将文本解析为字符串类型
          val text: String = Source.fromFile(fileName).mkString
          //2,首先获取到每一行的内容,使用windows的换行符\r\n ,Linux系统换行符\n
          val lines: Array[String] = text.split("\r\n")
          //3,对每一行进行切分,文本内容是按照空格切分,获取所有单词
          val words: Array[String] = lines.flatMap(_.split(" "))
          //4,把获取到的每一个单词的值都置为1,通过元组的格式
          val wordAndOne: Array[(String, Int)] = words.map((_, 1))
          //5,把相同单词进行分组
          val wordGroup: Map[String, Array[(String, Int)]] = wordAndOne.groupBy(_._1)
          //6,统计每个单词出现的次数,
          val result: Map[String, Int] = wordGroup.mapValues(_.length)
          println(result.toBuffer)
        }
      }
    }
  }
}


object WordCount2 {
  def main(args: Array[String]): Unit = {
    //创建actor
    val task = new StandAloneTask
    //启动Task
    task.start()
    //发送消息
    task !! SubmitTask("D:\\mappertest\\index\\input\\file1.txt")
  }
}