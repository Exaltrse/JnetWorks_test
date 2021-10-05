package com.ushkov.services;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.ushkov.exceptions.IllegalRequestParamException;
import com.ushkov.exceptions.IncompatiblePageNumberFormatException;

public class PageNumberService {

    private PageNumberService(){}

    /**
     * Reduce list of page numbers for using in print dialog by grouping consecutive numbers.
     *
     * @param pageNumbers   String of comma separated page numbers.
     * @return              String of reduced and sorted for print dialog page numbers. Example: "1,5-12,7,16".
     */
    public static String reducePageNumbers(String pageNumbers){
        if(pageNumbers == null) throw new IllegalRequestParamException("List of page numbers must not be null.");
        if(pageNumbers.isEmpty()) throw new IllegalRequestParamException("List of page numbers must not be empty.");
        //Convert requested string to list of page numbers.
        List<Integer> pageNumbersIntegerList = new ArrayList<>();
        try{
            pageNumbersIntegerList = Arrays.stream(pageNumbers.split(",")).map(Integer::parseInt).sorted().collect(Collectors.toList());
        }
        catch (NumberFormatException ex) {
            throw new IncompatiblePageNumberFormatException(IncompatiblePageNumberFormatException.NOTDIGITPAGENUMBER);
        }
        //Return result if request contain only one page number.
        if(pageNumbersIntegerList.size()==1) return pageNumbersIntegerList.get(0).toString();
        //Сhecking zero page number.
        if(pageNumbersIntegerList.get(0) == 0) throw new IncompatiblePageNumberFormatException(IncompatiblePageNumberFormatException.ZEROPAGENUMBER);
        //Сhecking negative page numbers.
        if(pageNumbersIntegerList.get(0) < 0) throw new IncompatiblePageNumberFormatException(IncompatiblePageNumberFormatException.NEGATIVEPAGENUMBER);

        //Using deque for access to begining and ending of queue.
        Deque<Integer> deque = new ArrayDeque<>();
        Iterator<Integer> iterator = pageNumbersIntegerList.listIterator();
        StringBuilder resultString = new StringBuilder();
        while(iterator.hasNext()){
            int i = iterator.next();
            if(deque.isEmpty()) deque.addLast(i);
            else{
                if(i == deque.getLast() + 1) deque.addLast(i); //Put next consecutive page number in queue.
                else{
                    if(i != deque.getLast()){
                        //If next page number is not consecutive for previous page numbers:
                        //- grouping numbers already containing in queue and add to result;
                        //- "clear" queue;
                        //- adding the new page number in the queue.
                        if(deque.size()>1) resultString.append(deque.getFirst() + "-" + deque.getLast() + ",");
                        else resultString.append(deque.getFirst() + ",");
                        deque = new ArrayDeque<>();
                        deque.addLast(i);
                    }
                    //If the next page number is already in queue - do nothing.
                }
                //If it is the last iteration - put information from the queue to result.
                if(!iterator.hasNext()){
                    if(deque.size()>1) resultString.append(deque.getFirst() + "-" + deque.getLast());
                    else resultString.append(deque.getFirst());
                }
            }
        }
        return resultString.toString();
    }
}
