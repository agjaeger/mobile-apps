package com.example.homework5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.chip.Chip;
import androidx.core.content.ContextCompat;

import android.app.Activity;

class IngredientAddChipOnClickListener implements View.OnClickListener
{
    String chipText;
    ChipGroup cg;
    Chip chip;

    public IngredientAddChipOnClickListener(ChipGroup cg, String chipText) {
        this.cg = cg;
        this.chipText = chipText;
    }

    @Override
    public void onClick(View v)
    {
        if (((CheckBox) v).isChecked()) {
            this.chip = new Chip(this.cg.getContext());
            this.chip.setText(this.chipText);
            this.cg.addView(chip);
        } else {
            this.cg.removeView(this.chip);
        }
    }
};

class IngredientAddChipOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{

    ChipGroup cg;
    Chip noCheeseChip;
    Chip cheeseChip;
    Chip doubleCheeseChip;

    public IngredientAddChipOnCheckedChangeListener(ChipGroup cg) {
        this.cg = cg;

        this.noCheeseChip = new Chip(this.cg.getContext());
        this.noCheeseChip.setText("No Cheese");

        this.cheeseChip = new Chip(this.cg.getContext());
        this.cheeseChip.setText("Cheese");

        this.doubleCheeseChip = new Chip(this.cg.getContext());
        this.doubleCheeseChip.setText("2x Cheese");;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if (checkedId == R.id.id_no_cheese_radio_button) {
            this.cg.addView(this.noCheeseChip);
            this.cg.removeView(this.cheeseChip);
            this.cg.removeView(this.doubleCheeseChip);
        }

        else if (checkedId == R.id.id_cheese_radio_button) {
            this.cg.removeView(this.noCheeseChip);
            this.cg.addView(this.cheeseChip);
            this.cg.removeView(this.doubleCheeseChip);
        }

        else if (checkedId == R.id.id_double_cheese_radio_button) {
            this.cg.removeView(this.noCheeseChip);
            this.cg.removeView(this.cheeseChip);
            this.cg.addView(this.doubleCheeseChip);
        }

        else {
            this.cg.removeView(this.noCheeseChip);
            this.cg.removeView(this.cheeseChip);
            this.cg.removeView(this.doubleCheeseChip);
        }
    }
}

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI Elements
        Button roundButton = findViewById(R.id.id_button_round);
        Button squareButton = findViewById(R.id.id_button_square);
        Button placeOrderButton = findViewById(R.id.id_place_order);

        CheckBox pepperoniCheckbox = findViewById(R.id.id_pepperoni_checkbox);
        CheckBox mushroomCheckbox = findViewById(R.id.id_mushroom_checkbox);
        CheckBox veggiesCheckbox = findViewById(R.id.id_vegetable_checkbox);
        CheckBox anchoviesCheckbox = findViewById(R.id.id_anchovies_checkbox);

        RadioGroup cheeseRadioGroup = findViewById(R.id.id_cheese_radio_group);

        final ImageView pizzaImg = findViewById(R.id.id_pizza_img);
        final ChipGroup chipGroup = findViewById(R.id.id_chipgroup);
        final EditText userName = findViewById(R.id.id_user_name);
        final EditText userPhone = findViewById(R.id.id_user_phone);

        /* -------------------------------------------------------------------
            Listeners
         */

        pepperoniCheckbox.setOnClickListener(new IngredientAddChipOnClickListener(chipGroup,"Pepperoni"));
        mushroomCheckbox.setOnClickListener(new IngredientAddChipOnClickListener(chipGroup,"Mushroom"));
        veggiesCheckbox.setOnClickListener(new IngredientAddChipOnClickListener(chipGroup,"Veggies"));
        anchoviesCheckbox.setOnClickListener(new IngredientAddChipOnClickListener(chipGroup,"Anchovies"));

        cheeseRadioGroup.setOnCheckedChangeListener(new IngredientAddChipOnCheckedChangeListener(chipGroup));

        squareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                pizzaImg.setScaleX(1);
                pizzaImg.setRotation(0);
                pizzaImg.setImageResource (R.drawable.ic_squared_pizza);
            }
        });

        roundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizzaImg.setScaleX(1);
                pizzaImg.setRotation(0);
                pizzaImg.setImageResource (R.drawable.ic_round_pizza);
            }
        });

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().length() == 0) {
                    userName.setError("This is required!");
                }

                if (userPhone.getText().length() == 0) {
                    userPhone.setError("This is required!");
                }

                if (userPhone.getText().length() != 10) {
                    userPhone.setError("Phone number is not length 10!");
                }
            }
        });
    }
}
