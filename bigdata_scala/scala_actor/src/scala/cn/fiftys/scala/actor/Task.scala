package cn.fiftys.scala.actor

import scala.actors.{Actor, Future}
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.Source


case class SubmitTasks(fileName: String)

case class ResultTask(result: Map[String, Int])

/**
  * wordcount案例,读取多个文件完成单词统计计数
  */
class Task extends Actor {
  override def act(): Unit = {
    loop {
      react {
        case SubmitTasks(fileName) => {
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
          //7,返回消息给发送方
          sender ! ResultTask(result)
        }
      }
    }
  }
}

object wordCount {
  def main(args: Array[String]): Unit = {
    //6,定义一个set集合,用于存放Future
    val replySet = new mutable.HashSet[Future[Any]]
    //12,定义一个List集合用于存放统计完成的数据
    val resultList = new ListBuffer[ResultTask]
    //1,准备数据文件
    val files = Array("D:\\file1.txt", "D:\\file2.txt", "D:\\file3.txt")
    //2,遍历文件数组,提交任务
    for (file <- files) {
      //3,创建actor
      val task = new Task
      //4,启动actor
      task.start()
      //5,发送消息
      val replay: Future[Any] = task !! SubmitTasks(file)
      //7,添加结果到set集合中
      replySet += replay
    }
    //8,遍历返回结果的replySet
    while (replySet.size > 0) {
      //9,过滤出真正有数据的Futuer
      val toCumpute = replySet.filter(_.isSet)
      //10,遍历toCompleted集合
      for (t <- toCumpute) {
        //11,获取Future中真正的数据
        val result = t.apply()
        resultList += result.asInstanceOf[ResultTask]
        replySet.remove(t)
      }
    }
    val finalResult: Map[String, Int] = resultList.map(_.result).flatten.groupBy(_._1).mapValues(x => x.foldLeft(0)(_ + _._2))
    println(finalResult)
  }
}