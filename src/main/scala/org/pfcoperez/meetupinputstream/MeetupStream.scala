package org.pfcoperez.meetupinputstream

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.ReceiverInputDStream

import scala.util.Try
import scalawebsocket.WebSocket

object MeetupStream {

  def apply(storageLevel: StorageLevel = StorageLevel.MEMORY_ONLY)(
           implicit streamingContext: StreamingContext
  ): ReceiverInputDStream[String] = streamingContext.receiverStream(new MeetupStream(storageLevel))

  def apply(implicit streamingContext: StreamingContext): ReceiverInputDStream[String] = apply()(streamingContext)

  private val meetupUrl = "ws://stream.meetup.com/2/rsvps"
}

class MeetupStream(
                    storageLevel: StorageLevel = StorageLevel.MEMORY_ONLY
                  ) extends Receiver[String](storageLevel) {

  import MeetupStream.meetupUrl

  var ws: Option[WebSocket] = None

  override def onStart(): Unit = {

    ws = Try {
      WebSocket().open(meetupUrl)
    } toOption //TODO: Recover with

    ws foreach (_ onTextMessage store) //On text message callback `x => store(x)` will be asynchronously executed

  }

  override def onStop(): Unit = ws foreach (_ close)

}
