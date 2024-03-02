package com.example.tugas01;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rgBurger;
    private RadioGroup rgrMembership;
    private EditText etQuantity;
    private Button btnOrder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgBurger = findViewById(R.id.rgBurger);
        rgrMembership = findViewById(R.id.rgrMembership);
        etQuantity = findViewById(R.id.etQuantity);
        btnOrder = findViewById(R.id.btnOrder);



        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {calculateTotal();
            }
        });
    }

    private void calculateTotal() {
        try {
            int selectedRadioButtonId = rgBurger.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            String burgerType = selectedRadioButton.getText().toString();

            int quantity = Integer.parseInt(etQuantity.getText().toString());
            double pricePerBurger = getPricePerBurger(burgerType);
            double totalAmount = quantity * pricePerBurger;


            double discount = (totalAmount >= 70000) ? totalAmount * 0.1 : 0;


            double membershipDiscount = getMembershipDiscount();
            totalAmount -= membershipDiscount;


            String receiptText =
                    "Nama Burger   : " + burgerType + "\n" +
                            "Jumlah Burger : " + quantity + "\n" +
                            "Total Harga   : Rp " + totalAmount + "\n" +
                            "Diskon        : Rp " + discount + "\n" +
                            "Pemotongan    : Rp " + membershipDiscount + "\n" +
                            "Total Bayar   : Rp " + (totalAmount - discount - membershipDiscount) + "\n\n" +
                            "Terima kasih sudah berbelanja. Selamat menikmati!";
            showReceipt(receiptText);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Masukkan jumlah yang valid", Toast.LENGTH_SHORT).show();
        }
    }


    private void showReceipt(String receiptText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Struk Pembelian BurgerQueen");
        builder.setMessage(receiptText);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private double getPricePerBurger(String burgerType) {
        switch (burgerType) {
            case "Chicken Burger":
                return 15000.0;
            case "Beef Burger":
                return 18000.0;
            case "Veggie Burger":
                return 12000.0;
            default:
                return 0.0;
        }
    }

    private double getMembershipDiscount() {
        int selectedRadioButtonId = rgrMembership.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

        if (selectedRadioButton != null) {
            String membershipType = selectedRadioButton.getText().toString();

            switch (membershipType) {
                case "Regular":
                    return 0.0;
                case "Gold":
                    return 30000.0;
                case "Silver":
                    return 10000.0;
                default:
                    return 0.0;
            }
        } else {
            return 0.0;



        }
    }
}