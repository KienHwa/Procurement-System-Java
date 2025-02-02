package oodjassignmentfinal;

import java.util.*;

abstract public class CRUD {
    protected String id;
    protected String filePath;
    
    abstract public String getFormat(String[] dataInfo);
    abstract public void createFile(String[] dataInfo);
    abstract public ArrayList<String[]> readFile();
    abstract public void updateFile(String[] dataInfo, String id);
    abstract public void deleteFile(String id);
}