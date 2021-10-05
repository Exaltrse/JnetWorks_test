package com.ushkov.services;

import java.util.ArrayList;
import java.util.Arrays;
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
            pageNumbersIntegerList =
                    Arrays.stream(pageNumbers.split(","))
                            .map(Integer::parseInt)
                            .sorted()
                            .distinct()
                            .collect(Collectors.toList());
        }
        catch (NumberFormatException ex) {
            throw new IncompatiblePageNumberFormatException(IncompatiblePageNumberFormatException.NOTDIGITPAGENUMBER);
        }
        //Return result if request contain only one page number.
        if(pageNumbersIntegerList.size()==1) return pageNumbersIntegerList.get(0).toString();
        //Сhecking zero page number.
        if(pageNumbersIntegerList.get(0) == 0)
            throw new IncompatiblePageNumberFormatException(IncompatiblePageNumberFormatException.ZEROPAGENUMBER);
        //Сhecking negative page numbers.
        if(pageNumbersIntegerList.get(0) < 0)
            throw new IncompatiblePageNumberFormatException(IncompatiblePageNumberFormatException.NEGATIVEPAGENUMBER);

        StringBuilder resultString = new StringBuilder();
        int startIndex = 0;
        int endIndex = 0;
        while(startIndex<pageNumbersIntegerList.size()){
            while(++endIndex<pageNumbersIntegerList.size()
                    && pageNumbersIntegerList.get(endIndex) - pageNumbersIntegerList.get(endIndex - 1) == 1);
            //
            if(endIndex - startIndex > 1){
                resultString.append(
                                ","
                                + pageNumbersIntegerList.get(startIndex)
                                + "-"
                                + pageNumbersIntegerList.get(endIndex - 1));
                startIndex = endIndex;
            }else resultString.append("," + pageNumbersIntegerList.get(startIndex++));
        }
        return resultString.substring(1);
    }
}
