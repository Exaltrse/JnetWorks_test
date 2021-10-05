package com.ushkov.services;

import java.util.ArrayDeque;
import java.util.ArrayList;
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
        List<Integer> pageNumbersIntegerList = new ArrayList<>();
        for(String s : pageNumbers.split(",")){
            try{
                pageNumbersIntegerList.add(Integer.parseInt(s));
            }
            catch (NumberFormatException ex){
                throw new IncompatiblePageNumberFormatException(IncompatiblePageNumberFormatException.NOTDIGITPAGENUMBER);
            }
        }
        if(pageNumbersIntegerList.size()==1) return pageNumbersIntegerList.get(0).toString();
        pageNumbersIntegerList = pageNumbersIntegerList.stream().sorted().collect(Collectors.toList());
        if(pageNumbersIntegerList.get(0) == 0)
            throw new IncompatiblePageNumberFormatException(IncompatiblePageNumberFormatException.ZEROPAGENUMBER);
        if(pageNumbersIntegerList.get(0) < 0)
            throw new IncompatiblePageNumberFormatException(IncompatiblePageNumberFormatException.NEGATIVEPAGENUMBER);

        Deque<Integer> deque = new ArrayDeque<>();
        Iterator<Integer> iterator = pageNumbersIntegerList.listIterator();
        StringBuilder resultString = new StringBuilder();
        while(iterator.hasNext()){
            int i = iterator.next();
            if(deque.isEmpty()) deque.addLast(i);
            else{
                if(iterator.hasNext()){
                    if(i == deque.getLast() + 1) deque.addLast(i);
                    else{
                        if(i != deque.getLast()){
                            if(deque.size()>1) {
                                resultString.append(deque.getFirst());
                                resultString.append("-");
                                resultString.append(deque.getLast());
                                resultString.append(",");
                            }else{
                                resultString.append(deque.getFirst());
                                resultString.append(",");
                            }
                            deque = new ArrayDeque<>();
                            deque.addLast(i);
                        }
                    }
                }else{
                    if(i == deque.getLast() + 1) deque.addLast(i);
                    else{
                        if(i != deque.getLast()){
                            if(deque.size()>1) {
                                resultString.append(deque.getFirst());
                                resultString.append("-");
                                resultString.append(deque.getLast());
                                resultString.append(",");
                            }else{
                                resultString.append(deque.getFirst());
                                resultString.append(",");
                            }
                            deque = new ArrayDeque<>();
                            deque.addLast(i);
                        }
                    }
                    if(deque.size()>1){
                        resultString.append(deque.getFirst());
                        resultString.append("-");
                        resultString.append(deque.getLast());
                    }
                    else{
                        resultString.append(deque.getFirst());
                    }
                }
            }
        }
        return resultString.toString();
    }
}
