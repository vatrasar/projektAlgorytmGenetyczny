package org.example.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DataValidation implements ChangeListener<String> {

    final TextField textField;
    final int digNumber;
    final boolean isInteger;
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue,
                        String newValue)  {
        String regex="\\d*.\\d*";
        if(isInteger)
        {
            regex="\\d*";
        }

        if(newValue.length()>digNumber)
        {
            textField.setText(oldValue);
        }
        else if (!newValue.matches(regex) && newValue.length()!=0) {
            textField.setText(oldValue);
        }



    }
}
