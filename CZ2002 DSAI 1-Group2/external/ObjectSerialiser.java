package external;

import mgr.*;
import entities.*;
import ui.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Responsible solely for Serializing and deserializing objects
 */
public class ObjectSerialiser {

    /**
     * To deserialise an object based on a given path to the file
     * @param address Location of file to be deserialise
     * @return Object that will be return
     */
    public Object deserialiseItem(String address){
        Object o;
        try{
            ObjectInputStream in=new ObjectInputStream(new FileInputStream(address));
            o = in.readObject();
        }
        catch(Exception e){
            return null;
        }
        return o;
    }

    /**
     * To serialise an object based on the given object
     * @param o that to be serialised
     * @param name of file to be saved
     */
    public void serialiseItem(Object o, String name){
        //Updating the serialized file to contain all the new updates to the system
        String filename = name + ".ser";
        try{
            //Creating the object
            //Creating stream and writing the object
            FileOutputStream fout = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(o);
            out.flush();
            //closing the stream
            out.close();

        }catch(Exception e){
            System.out.println("Unable to serialise object");
            e.getMessage();
        }
        return;
    }
}