package org.pfcoperez.meetupinputstream.samples

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.Duration._
import org.pfcoperez.meetupinputstream.MeetupStream

object SimpleStream extends App {

  val conf = new SparkConf() setMaster("local[2]") setAppName("Meetup stream sample")

  val sc = new StreamingContext(conf, Seconds(1))

  val stream = MeetupStream(sc)

  stream.print

  sc.start()

  sc.awaitTermination

}
