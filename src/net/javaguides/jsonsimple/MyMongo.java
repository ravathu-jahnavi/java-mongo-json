package net.javaguides.jsonsimple;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MyMongo {
    File file;
    JSONArray userList = new JSONArray();

    /**
     * MyMongo method (constructor) here creates a new collection with the name given in parameter.
     * @param filename
     */
    MyMongo(String filename)
    {
        try {
            this.file = new File("./"+filename);
            if(file.createNewFile()) {
                System.out.println("File successfully created!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * insert function is used here to add single document to a collection
     * @param id
     * @param firstname
     * @param lastname
     * @param email
     * @return true or false
     */
    public boolean insert(int id, String firstname, String lastname, String email)
    {
        JSONObject userDetails = new JSONObject();
        userDetails.put("id", id);
        userDetails.put("firstName", firstname);
        userDetails.put("lastName", lastname);
        userDetails.put("email", email);
        this.userList.add(userDetails);

        try(FileWriter wfile = new FileWriter(this.file)) {
                wfile.write(String.valueOf(this.userList));
                wfile.flush();
                return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * find function with no parameters is used to display all documents inside the collection
     * does not take any parameters
     * @return JSONArray
     */
    public JSONArray find()
    {
        JSONParser parser = new JSONParser();
        JSONArray arr = new JSONArray();
        Object obj = null;
        try {
            //System.out.println(this.userList);
            obj = parser.parse(new FileReader(this.file));
            arr = (JSONArray) obj;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            obj = new Object();
            System.out.println("empty");
        }
        return arr;
    }

    /**
     * remove method is used to delete the content of json file - deleting documents from collection
     * @return true or false
     */
    public boolean remove()
    {
        try (FileWriter file = new FileWriter(this.file))
        {
            file.write("");
            file.flush();
            return true;
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * drop method is used to delete collection
     * @return true or false
     */
    public boolean drop()
    {
        try
        {
            return this.file.delete();
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * to update value of a key based on id
     * @param id
     * @param key
     * @param value
     * @return
     */
    public boolean update(int id, String key, String value) {
        JSONArray arr = find();
        try{
            for (int i = 0; i < arr.size(); i++)
            {
                JSONObject item = (JSONObject) arr.get(i);
                if(item.containsValue(id))
                {
                    item.remove(key);
                    item.put(key,value);
                }
            }
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
