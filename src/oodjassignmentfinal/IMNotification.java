package oodjassignmentfinal;

import java.io.*;
import java.util.*;

public class IMNotification extends Notification{
    
    @Override
    public String getNotification(){
        ItemsOrdered itemsOrderedMethod = new ItemsOrdered();
        
        List<String[]> pendingUpdates = itemsOrderedMethod.readFile();
        return String.valueOf(pendingUpdates.size());
    }
}
