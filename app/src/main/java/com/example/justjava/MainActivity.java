package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    static final int PRICE_PER_CUP = 5;
    static final int WHIPPED_CREAM_TOPPING_PRICE_PER_CUP = 1;
    static final int CHOCOLATE_TOPPING_PRICE_PER_CUP = 2;
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
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        //code for the whipped cream checkbox to access its value
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //code for the chocolate checkbox to access its value
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();


        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        //displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

        /**
         * This method is called when the plus button is clicked.
         */
    public void increment(View view){
        if (quantity == 100){
            //show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // exit this method early because there is nothing left to do
            return; //we can do that because this methods doesn't return anything
        }
        quantity += 1;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view){
        if (quantity == 1){
            // show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // exit this method early because there is nothing left to do
            return; //we can do that because this methods doesn't return anything
        }
        quantity -= 1;
        display(quantity);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @return the price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = PRICE_PER_CUP;

        if(addWhippedCream){
            basePrice += WHIPPED_CREAM_TOPPING_PRICE_PER_CUP;
        }

        if(addChocolate){
            basePrice += CHOCOLATE_TOPPING_PRICE_PER_CUP;
        }

        return basePrice * quantity;
    }

    /**
     * create a summary of the order
     *
     * @param price of the order
     * @param addWhippedCream equals true if the checkbox is checked, false otherwise
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param nameOfCustomer is the name of the customer
     * @return text summary of the order
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate,
                                      String nameOfCustomer){
        String summary;
        summary = getString(R.string.order_summary_name, nameOfCustomer);
        summary += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        summary += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        summary += "\n" + getString(R.string.order_summary_quantity, quantity);
        summary += "\n" + getString(R.string.order_summary_price, price) + " €";
        summary += "\n" + getString(R.string.thank_you);
        return summary;
    }

}
