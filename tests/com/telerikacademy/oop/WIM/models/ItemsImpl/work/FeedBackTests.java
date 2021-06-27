package com.telerikacademy.oop.WIM.models.ItemsImpl.work;

import com.telerikacademy.oop.WIM.models.common.enums.*;
import com.telerikacademy.oop.WIM.CommonConstants;
import com.telerikacademy.oop.WIM.models.contracts.items.Bug;
import com.telerikacademy.oop.WIM.models.contracts.items.FeedBack;
import com.telerikacademy.oop.WIM.models.contracts.items.Story;
import com.telerikacademy.oop.WIM.models.contracts.items.WorkItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.telerikacademy.oop.WIM.CommonConstants.*;
import static com.telerikacademy.oop.WIM.CommonConstants.steps;
import static com.telerikacademy.oop.WIM.models.common.Constants.FB_RATTING_MAST_BE_INTEGER;

public class FeedBackTests {

    private final String status = FeedBackStatus.DONE.toString();
    private final int rating = 5;

    @Test//constructor
    public void Constructor_Should_CreateFB_When_CorrectInput() {

        FeedBackImpl newFB = new FeedBackImpl(LEN_10, LEN_10, status, rating);

        Assertions.assertEquals(LEN_10, newFB.getTitle());
        Assertions.assertEquals(LEN_10, newFB.getDescription());
        Assertions.assertEquals(status, newFB.getStatus());
        Assertions.assertEquals(rating, newFB.getRating());
    }

    @Test
    public void Constructor_Should_Throw_When_LowSizeInput1() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new FeedBackImpl(LEN_2, LEN_10, status, rating));
    }

    @Test
    public void Constructor_Should_Throw_When_LowSizeInput2() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new FeedBackImpl(LEN_10, LEN_2, status, rating));
    }

    @Test
    public void Constructor_Should_Throw_When_HighSizeInput1() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new FeedBackImpl(LEN_55, LEN_10, status, rating));
    }

    @Test
    public void Constructor_Should_Throw_When_HighSizeInput2() {

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new FeedBackImpl(LEN_10, LEN_550, status, rating));
    }

    @Test
    public void Constructor_Should_Throw_When_WrongStatus() {
        String wrongStatus = "wrong";

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new FeedBackImpl(LEN_10, LEN_550, wrongStatus, rating));
    }

    @Test//status
    public void ChangeField_Should_ChangeStatusCorrectly_When_CorrectInput() {

        WorkItem FB = new FeedBackImpl(LEN_10, LEN_10, status, rating);

        FB.assignNewValueToAccessibleField(OpenField.STATUS,
                FeedBackStatus.UNSCHEDULED.toString());

        Assertions.assertEquals(FeedBackStatus.UNSCHEDULED.toString(),
                FB.getStatus());
    }

    @Test
    public void ChangeField_Should_ChangeStatusCorrectly_When_CorrectInput2() {

        WorkItem FB = new FeedBackImpl(LEN_10, LEN_10, status, rating);

        FB.assignNewValueToAccessibleField(OpenField.STATUS,
                "-");

        Assertions.assertEquals(FeedBackStatus.SCHEDULED.toString(),
                FB.getStatus());
    }

    @Test
    public void ChangeField_Should_NotThrow_When_InputCantChangeStatusMore() {

        WorkItem FB = new FeedBackImpl(LEN_10, LEN_10, status, rating);


        FB.assignNewValueToAccessibleField(OpenField.STATUS, "+");
        FB.assignNewValueToAccessibleField(OpenField.STATUS, "+");
        FB.assignNewValueToAccessibleField(OpenField.STATUS, "+");
        FB.assignNewValueToAccessibleField(OpenField.STATUS, "+");
        FB.assignNewValueToAccessibleField(OpenField.STATUS, "+");

        Assertions.assertEquals(FeedBackStatus.DONE.toString(),
                FB.getStatus());
    }

    @Test
    public void ChangeField_Should_NotThrow_When_InputCantChangeStatusMore2() {

        WorkItem FB = new FeedBackImpl(LEN_10, LEN_10, status, rating);


        FB.assignNewValueToAccessibleField(OpenField.STATUS, "Done");

        Assertions.assertEquals(FeedBackStatus.DONE.toString(),
                FB.getStatus());
    }

    @Test//rating
    public void ChangeField_Should_ChangeRatingCorrectly_When_CorrectInput() {

        FeedBack FB = new FeedBackImpl(LEN_10, LEN_10, status, rating);
        String newRating = "12";

        FB.assignNewValueToAccessibleField(OpenField.RATING,
                newRating);

        Assertions.assertEquals(newRating,
                "" + FB.getRating());
    }

    @Test
    public void ChangeField_Should_ChangeRatingCorrectly_When_CorrectInput2() {

        FeedBack FB = new FeedBackImpl(LEN_10, LEN_10, status, rating);

        FB.assignNewValueToAccessibleField(OpenField.RATING,
                "-");

        Assertions.assertEquals(rating,
                FB.getRating() + 1);
    }

    @Test
    public void ChangeField_Should_ChangeRatingCorrectly_When_CorrectInput3() {

        FeedBack FB = new FeedBackImpl(LEN_10, LEN_10, status, rating);

        FB.assignNewValueToAccessibleField(OpenField.RATING,
                "+");

        Assertions.assertEquals(rating,
                FB.getRating() - 1);
    }

    @Test
    public void ChangeField_Should_NotThrow_When_InputCantChangeSizeMore() {

        FeedBack FB = new FeedBackImpl(LEN_10, LEN_10, status, rating);

        FB.assignNewValueToAccessibleField(OpenField.RATING,
                "5");

        Assertions.assertEquals(rating,
                FB.getRating() );
    }

    @Test
    public void ChangeField_Should_NotThrow_When_WrongInput() {

        FeedBack FB = new FeedBackImpl(LEN_10, LEN_10, status, rating);
        String wrongInput = "wrong";

        String actualOutput = FB.assignNewValueToAccessibleField(OpenField.RATING,
                wrongInput);
        String expectedOutput = FB_RATTING_MAST_BE_INTEGER;

        Assertions.assertEquals(expectedOutput,
                actualOutput);
    }


    @Test
    public void ToString_Should_GiveCorrectOutput_When_Called() {

        FeedBack fb = new FeedBackImpl(LEN_10, LEN_10, FeedBackStatus.DONE.toString(), rating);
        String expected = String.format("Feedback ID %d, 1234567890, status Done," +
                        " description 1234567890, rating 5" + System.lineSeparator(),
                WorkItemBase.getNumberOfItems());

        String actual = fb.toString();

        Assertions.assertEquals(expected,
                actual);
    }


    @Test
    public void OrderingWI_Should_GiveSortByRating_When_CalledOnFeedback(){
        FeedBack fb = new FeedBackImpl(LEN_10, LEN_10, status, 3);
        FeedBack fb1 = new FeedBackImpl(LEN_10, LEN_10, status, 2);
        FeedBack fb2 = new FeedBackImpl(LEN_10, LEN_10, status, 5);
        FeedBack fb3 = new FeedBackImpl(LEN_10, LEN_10, status, 55);

        List<FeedBack> list = List.of(fb3,fb,fb1,fb2);

        List<FeedBack> expected = List.of(fb1,fb,fb2,fb3);
        List<FeedBack> actual =  list.stream().sorted(FeedBack::compareTo).collect(Collectors.toList());

        Assertions.assertEquals(expected,actual);
    }

}
