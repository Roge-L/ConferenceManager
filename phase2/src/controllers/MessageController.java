package controllers;

import entities.*;
import useCases.MessageManager;

import java.util.ArrayList;

import static useCases.EventManager.getAttendingSpecificEvent;
import static useCases.EventManager.giveEventIDOfTitle;
import static useCases.MessageManager.makeNewMessage;
import static useCases.UserManager.getAllUsers;
import static useCases.UserManager.giveIDOfUsername;

/** A message controller which handles user input for message content */
public class MessageController {

    /**
     * Call this method anytime a message needs to be created
     * the method determines whether the message is new or a reply and creates a
     * Message object accordingly
     *
     * @param senderUsername - the username of the sender
     * @param receiverUsername - the username of the receiver
     * @param replyToID  - the ID of the message to which we are replying (if applicable)
     * @param content    - the content of the message
     */

    public static void createNewMessage(String senderUsername, String receiverUsername, int statusID,
                                         int replyToID, String content) {
        int senderID = giveIDOfUsername(senderUsername);
        int receiverID = giveIDOfUsername(receiverUsername);
        makeNewMessage(senderID, statusID, receiverID, replyToID, content);
    }

    /**
     * An organizer can message all the speakers
     * @param senderUsername - username of sender
     * @param content - content of message
     */
    public static void messageAllSpeakers(String senderUsername, String content){
        int senderID = giveIDOfUsername(senderUsername);
        for (User i : getAllUsers()) { //goes through each User one by one
                                        //from UserManager
            if (i instanceof Speaker) { //if the current user is an instance of Speaker
                makeNewMessage(senderID, 0, i.getUserID(),
                        -1, content);
                //create the message with no reply
            }
        }
    }

    /**
     * An organizer can message all the attendees
     * @param senderUsername - username of sender
     * @param content - content of message
     */
    public static void messageAllAttendees(String senderUsername, String content){
        int senderID = giveIDOfUsername(senderUsername);
        for (User i : getAllUsers()) {//goes through each User one by one
                                        //from UserManager
            if (i instanceof Attendee) { //if the current user is an instance of Attendee
                makeNewMessage(senderID, 0, i.getUserID(),
                        -1, content);
                //create a message with no reply
            }
        }
    }

    /**
     * Messages every attendee attending the event with EventID
     * @param senderUsername - username of the sender (the speaker)
     * @param eventTitle - title of the event
     * @param content - content of the message
     */
    public static void messageAllAttendeesOfTalk(String senderUsername, String eventTitle, String content){
        int eventID = giveEventIDOfTitle(eventTitle);
        ArrayList<Integer> attendees = getAttendingSpecificEvent(eventID);
        //list of all attendeeIDs attending
        int senderID = giveIDOfUsername(senderUsername);

        for (Integer attendeeID: attendees){
            makeNewMessage(senderID, 0, attendeeID, -1, content);
            //for every attendee, create message with no reply
            //if attendees is empty, no messages will be created
        }
    }

    public static void markAsUnRead(Message message) {
        message.setStatusID(-3);
    }

    public static void deleteMessage(Message message) {

//      Calls the deleteReceivedMessage function from the MessageManager to set the statusID to -1 ie: deleted!
        MessageManager.deleteReceivedMessage(message.getMessageID());

//      Adds the deleted messages to the deleted messages list for the presenter to present the list.
        MessageManager.getAllDeletedMessages(message.getReceiverID()).add(message);
    }
}
