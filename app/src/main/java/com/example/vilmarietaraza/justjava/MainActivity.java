/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.vilmarietaraza.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText name = findViewById(R.id.name);
        String nameValue = name.getText().toString();

        CheckBox whippedCreamCheckBox = findViewById(R.id.whippedcream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        //Log.v("MainActivity","Has whipped cream?" + hasWhippedCream);

        CheckBox chocolateCheckbox = findViewById(R.id.chocolate);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(nameValue, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: "));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + nameValue);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);


    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean whippedCreamTopping, boolean chocolateTopping) {
        int basePrice = 5;


        if (whippedCreamTopping) {
            basePrice = basePrice + 1;
        }

        if (chocolateTopping) {
            basePrice = basePrice + 2;
        }

        return basePrice * quantity;
    }

    private String createOrderSummary(String userName, int price, boolean addWhippedCream, boolean addChocolate) {
        return "Name: " + userName + "\nAdd whipped cream? " + addWhippedCream + "\nAdd chocolate? " + addChocolate + "\nQuantity: " + quantity + "\nTotal: $" + price + "\nThank You!";
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method is called when plus button is pressed.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 coffes", Toast.LENGTH_SHORT).show();
        }


        quantity = quantity + 1;
        displayQuantity(quantity);


    }

    /**
     * This method is called when minus button is pressed.
     *
     * @param view
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than one coffee", Toast.LENGTH_SHORT).show();
        }

        quantity = quantity - 1;
        displayQuantity(quantity);

    }

}
