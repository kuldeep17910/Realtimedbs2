package com.suriyal.realtimedbs2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
{

    EditText e1,e2,e3;
    Button btn,btn2;


    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=findViewById(R.id.editTextNumber);
        e2=findViewById(R.id.editTextTextPersonName);
        e3=findViewById(R.id.editTextTextPersonName2);
        btn=findViewById(R.id.button);
        btn2=findViewById(R.id.button2);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Product_Details");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=Integer.parseInt(e1.getText().toString());
                String name=e2.getText().toString();
                String price=e3.getText().toString();

                //Insert record toproduct object
                Product product=new Product();
                product.setId(id);
                product.setName(name);
                product.setPrice(price);

                //Generate key for store unique product
          String childId=      reference.push().getKey();
          reference.child(childId).setValue(product);

                Toast.makeText(MainActivity.this, "Data Saved..", Toast.LENGTH_SHORT).show();

            }
        });
btn2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                  Product obj=  snapshot1.getValue(Product.class);
                    Toast.makeText(MainActivity.this, ""+obj.getId(), Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
});
    }
}