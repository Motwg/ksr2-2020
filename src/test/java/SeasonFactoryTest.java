import enumerate.Season;
import model.SeasonFactory;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class SeasonFactoryTest {

    @Test
    public void test() {
        LocalDateTime dayBeforeSpring = LocalDateTime.now().withDayOfMonth(20).withMonth(3);
        LocalDateTime dayStartingSpring = LocalDateTime.now().withDayOfMonth(21).withMonth(3);
        LocalDateTime dayAfterStartingSpring = LocalDateTime.now().withDayOfMonth(22).withMonth(3);
        LocalDateTime dayInSpring = LocalDateTime.now().withDayOfMonth(15).withMonth(5);

        Assert.assertEquals(SeasonFactory.getSeason(dayBeforeSpring), Season.Winter);
        Assert.assertEquals(SeasonFactory.getSeason(dayStartingSpring), Season.Spring);
        Assert.assertEquals(SeasonFactory.getSeason(dayAfterStartingSpring), Season.Spring);
        Assert.assertEquals(SeasonFactory.getSeason(dayInSpring), Season.Spring);

        LocalDateTime dayBeforeSummer = LocalDateTime.now().withDayOfMonth(21).withMonth(6);
        LocalDateTime dayStartingSummer = LocalDateTime.now().withDayOfMonth(22).withMonth(6);
        LocalDateTime dayAfterStartingSummer = LocalDateTime.now().withDayOfMonth(23).withMonth(6);
        LocalDateTime dayInSummer = LocalDateTime.now().withDayOfMonth(15).withMonth(8);

        Assert.assertEquals(SeasonFactory.getSeason(dayBeforeSummer), Season.Spring);
        Assert.assertEquals(SeasonFactory.getSeason(dayStartingSummer), Season.Summer);
        Assert.assertEquals(SeasonFactory.getSeason(dayAfterStartingSummer), Season.Summer);
        Assert.assertEquals(SeasonFactory.getSeason(dayInSummer), Season.Summer);

        LocalDateTime dayBeforeAutumn = LocalDateTime.now().withDayOfMonth(22).withMonth(9);
        LocalDateTime dayStartingAutumn = LocalDateTime.now().withDayOfMonth(23).withMonth(9);
        LocalDateTime dayAfterStartingAutumn = LocalDateTime.now().withDayOfMonth(24).withMonth(9);
        LocalDateTime dayInAutumn = LocalDateTime.now().withDayOfMonth(15).withMonth(11);

        Assert.assertEquals(SeasonFactory.getSeason(dayBeforeAutumn), Season.Summer);
        Assert.assertEquals(SeasonFactory.getSeason(dayStartingAutumn), Season.Autumn);
        Assert.assertEquals(SeasonFactory.getSeason(dayAfterStartingAutumn), Season.Autumn);
        Assert.assertEquals(SeasonFactory.getSeason(dayInAutumn), Season.Autumn);

        LocalDateTime dayBeforeWinter = LocalDateTime.now().withDayOfMonth(21).withMonth(12);
        LocalDateTime dayStartingWinter = LocalDateTime.now().withDayOfMonth(22).withMonth(12);
        LocalDateTime dayAfterStartingWinter = LocalDateTime.now().withDayOfMonth(23).withMonth(12);
        LocalDateTime dayInWinter = LocalDateTime.now().withDayOfMonth(15).withMonth(2);

        Assert.assertEquals(SeasonFactory.getSeason(dayBeforeWinter), Season.Autumn);
        Assert.assertEquals(SeasonFactory.getSeason(dayStartingWinter), Season.Winter);
        Assert.assertEquals(SeasonFactory.getSeason(dayAfterStartingWinter), Season.Winter);
        Assert.assertEquals(SeasonFactory.getSeason(dayInWinter), Season.Winter);
    }
}
