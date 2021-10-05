package com.ushkov;

import org.junit.Assert;
import org.junit.Test;

import com.ushkov.exceptions.IllegalRequestParamException;
import com.ushkov.exceptions.IncompatiblePageNumberFormatException;
import com.ushkov.services.PageNumberService;

public class PageNumberServiceJUnitTest extends Assert {

    @Test
    public void reducePageNumbers_OneNumber_PositiveTest(){
        String request = "10";
        String expected = "10";
        String actual = PageNumberService.reducePageNumbers(request);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void reducePageNumbers_OneRange_PositiveTest(){
        String request = "1,2,3,4,5,6,7,8,9,10";
        String expected = "1-10";
        String actual = PageNumberService.reducePageNumbers(request);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void reducePageNumbers_WithoutRange_PositiveTest(){
        String request = "1,3,5,7,9,11,13,15,17,19";
        String expected = "1,3,5,7,9,11,13,15,17,19";
        String actual = PageNumberService.reducePageNumbers(request);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void reducePageNumbers_SortedList_PositiveTest(){
        String request = "1,2,3,4,5,7,8,9,10";
        String expected = "1-5,7-10";
        String actual = PageNumberService.reducePageNumbers(request);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void reducePageNumbers_UnsortedList_PositiveTest(){
        String request = "5,12,66,12,3,7,78,4,11,65,9,1";
        String expected = "1,3-5,7,9,11-12,65-66,78";
        String actual = PageNumberService.reducePageNumbers(request);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void reducePageNumbers_Duplicates_PositiveTest(){
        String request = "1,2,12,4,2,6,8,9,10,12,12,7,12";
        String expected = "1-2,4,6-10,12";
        String actual = PageNumberService.reducePageNumbers(request);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalRequestParamException.class)
    public void reducePageNumbers_NullRequest_NegativeTest(){
        PageNumberService.reducePageNumbers(null);
    }

    @Test(expected = IllegalRequestParamException.class)
    public void reducePageNumbers_EmptyRequest_NegativeTest(){
        PageNumberService.reducePageNumbers("");
    }

    @Test(expected = IncompatiblePageNumberFormatException.class)
    public void reducePageNumbers_ZeroPageNumber_NegativeTest(){
        PageNumberService.reducePageNumbers("6,15,23,0,7");
    }

    @Test(expected = IncompatiblePageNumberFormatException.class)
    public void reducePageNumbers_NegativePageNumber_NegativeTest(){
        PageNumberService.reducePageNumbers("6,15,23,-7,5");
    }

    @Test(expected = IncompatiblePageNumberFormatException.class)
    public void reducePageNumbers_NondigitPageNumber_NegativeTest(){
        PageNumberService.reducePageNumbers("6,13,23,7b,8");
    }
}
