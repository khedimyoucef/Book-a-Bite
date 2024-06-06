package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class RestrauntCheckInActivity extends AppCompatActivity {
    ImageView restraunt_default_picture;
    FloatingActionButton add_picture_icon;

    EditText restrauntNameEt;
    Spinner wilayasSp;
    EditText streetNameEt;
    public static final String RESTRAUNT_NAME="restrauntName";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restraunt_check_in);
        init();
        //the wilayas:
        String[] wilayas = {
                "1. Adrar",
                "2. Chlef",
                "3. Laghouat",
                "4. Oum El Bouaghi",
                "5. Batna",
                "6. Béjaïa",
                "7. Biskra",
                "8. Béchar",
                "9. Blida",
                "10. Bouira",
                "11. Tamanrasset",
                "12. Tébessa",
                "13. Tlemcen",
                "14. Tiaret",
                "15. Tizi Ouzou",
                "16. Alger",
                "17. Djelfa",
                "18. Jijel",
                "19. Sétif",
                "20. Saïda",
                "21. Skikda",
                "22. Sidi Bel Abbès",
                "23. Annaba",
                "24. Guelma",
                "25. Constantine",
                "26. Médéa",
                "27. Mostaganem",
                "28. M'Sila",
                "29. Mascara",
                "30. Ouargla",
                "31. Oran",
                "32. El Bayadh",
                "33. Illizi",
                "34. Bordj Bou Arréridj",
                "35. Boumerdès",
                "36. El Tarf",
                "37. Tindouf",
                "38. Tissemsilt",
                "39. El Oued",
                "40. Khenchela",
                "41. Souk Ahras",
                "42. Tipaza",
                "43. Mila",
                "44. Aïn Defla",
                "45. Naâma",
                "46. Aïn Témouchent",
                "47. Ghardaïa",
                "48. Relizane",
                "49. Timimoun",
                "50. Bordj Badji Mokhtar",
                "51. Ouled Djellal",
                "52. Béni Abbès",
                "53. In Salah",
                "54. In Guezzam",
                "55. Touggourt",
                "56. Djanet",
                "57. El M'Ghair",
                "58. El Meniaa"
        };
        //fill the spinner:
        ArrayList<String> arrayList=new ArrayList<>(Arrays.asList(wilayas));
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getBaseContext(),R.layout.spinner_text_style,arrayList);
        wilayasSp.setAdapter(arrayAdapter);
        /////// pick the wilaya of the restraunt:
        String wilaya=wilayasSp.getSelectedItem().toString();
        // retreive the name and the address of the restraunt:
     String  restaurantName=restrauntNameEt.getText().toString();
     String  restaurantStreetName=streetNameEt.getText().toString();

     //to check later weather in ButtonOnClickListener or not

        //take the user into the map to detect their  location :

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getColor(R.color.bg_base_color1)));
        add_picture_icon.setOnClickListener(v -> {
            ImagePicker.with(RestrauntCheckInActivity.this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
            //comment2


        });
    }@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //the profile image of the restraunt :
        Uri uri =data.getData();
        restraunt_default_picture.setImageURI(uri);
    }
    protected void init(){
        restraunt_default_picture=findViewById(R.id.restraunt_default_picture);
        add_picture_icon=findViewById(R.id.add_picture_icon);
        restrauntNameEt=findViewById(R.id.restrauntNameEt);

        streetNameEt=findViewById(R.id.streetNameEt);


    }

}