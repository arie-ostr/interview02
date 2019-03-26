package picker;

import base.ElementMap;
import org.openqa.selenium.WebDriver;

public class DatePickerModule extends base.BaseModule {
    DatePickerMap map;

    public DatePickerModule(WebDriver driver) {
        super(driver);
        this.map = new DatePickerMap(driver);
    }
    //modelled after the behavior of : https://jqueryui.com/datepicker/
    public void Pick(int numberOfDaysFromToday) {
            int activeDay = Integer.parseInt(this.getMap().active_day_elem().getText());
            int daysInMonth = this.getMap().days_elems().size();
            int diff = daysInMonth - activeDay;
            int remaiiningDays = numberOfDaysFromToday;
            if (diff < numberOfDaysFromToday){
                this.getMap().days_elems().get(activeDay+diff-1).click();
                return;
            } else {
                while(remaiiningDays > diff) {
                    remaiiningDays -= diff;
                    this.getMap().next_month_btn_elem().click();
                    diff = this.getMap().days_elems().size();
                }
                this.getMap().days_elems().get(remaiiningDays-1).click();
            }
    }

    @Override
    public DatePickerMap getMap() {
        return this.map;
    }
}
