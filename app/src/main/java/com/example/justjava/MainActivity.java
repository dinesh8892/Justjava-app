package com.example.justjava;

/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

import android.content.Context;
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
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int PricePerCup = 5;

        CheckBox whipped_cream_checkbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean has_whipped_cream = whipped_cream_checkbox.isChecked();
        CheckBox chocolate_checkbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean chocolate = chocolate_checkbox.isChecked();
        EditText name = (EditText) findViewById(R.id.name);
        String my_name = name.getText().toString();
        int price = calculatePrice(PricePerCup, has_whipped_cream, chocolate);
        String message = Order_Summary(price, has_whipped_cream, chocolate, my_name);
        if(message != "") {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for " + my_name);
            intent.putExtra(Intent.EXTRA_TEXT, message);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
        }



    public void increment(View view) {
        if(quantity >= 100){
            quantity = 100;
            Context context = getApplicationContext();
            CharSequence text = "That's it! can't order more than this";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else{
            quantity = quantity + 1;
        }
        display(quantity);
    }

    public void decrement(View view) {
        if(quantity == 1){
            quantity = 1;
            Context context = getApplicationContext();
            CharSequence text = "Can't order below this";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else{
            quantity = quantity - 1;
        }

        display(quantity);
    }

    private int calculatePrice(int PricePerCup, boolean has_whipped_cream, boolean has_chocolate){
        if(has_whipped_cream){
            PricePerCup += 1;
        }
        if(has_chocolate){
            PricePerCup += 2;
        }

        return quantity * PricePerCup;
    }

    private String Order_Summary(int price, boolean has_whipped_cream, boolean chocolate, String my_name){
        String w;
        String c;
        if(my_name.equals("")){
            Context context = getApplicationContext();
            CharSequence text = "Please enter your name";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return "";
        }
        else{
            if(has_whipped_cream){
                w = "Yes";
            }
            else{
                w = "No";
            }
            if (chocolate){
                c = "Yes";
            }
            else{
                c = "No";
            }
            String message = "Name: "+ my_name+ "\n" + "Add whipped cream: " + w + "\nAdd Chocolate "+ c +"\nQuantity: " + quantity + "\nTotal: $" + price + "\nThank U!";
            return message;
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}