package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityRestaurantHomeBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class EditPhoto extends AppCompatActivity {
    ImageView restraunt_default_picture;
    FloatingActionButton add_picture_icon;
    TextView doneBtnTv;
    Spinner wilayasSp;
    EditText streetNameEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);
        init();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.bg_base_color1)));
        }

        add_picture_icon.setOnClickListener(v -> {
            ImagePicker.with(EditPhoto.this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start();
        });

        String[] wilayas = {
                "1. Adrar", "2. Chlef", "3. Laghouat", "4. Oum El Bouaghi", "5. Batna",
                "6. Béjaïa", "7. Biskra", "8. Béchar", "9. Blida", "10. Bouira",
                "11. Tamanrasset", "12. Tébessa", "13. Tlemcen", "14. Tiaret", "15. Tizi Ouzou",
                "16. Alger", "17. Djelfa", "18. Jijel", "19. Sétif", "20. Saïda",
                "21. Skikda", "22. Sidi Bel Abbès", "23. Annaba", "24. Guelma", "25. Constantine",
                "26. Médéa", "27. Mostaganem", "28. M'Sila", "29. Mascara", "30. Ouargla",
                "31. Oran", "32. El Bayadh", "33. Illizi", "34. Bordj Bou Arréridj", "35. Boumerdès",
                "36. El Tarf", "37. Tindouf", "38. Tissemsilt", "39. El Oued", "40. Khenchela",
                "41. Souk Ahras", "42. Tipaza", "43. Mila", "44. Aïn Defla", "45. Naâma",
                "46. Aïn Témouchent", "47. Ghardaïa", "48. Relizane", "49. Timimoun", "50. Bordj Badji Mokhtar",
                "51. Ouled Djellal", "52. Béni Abbès", "53. In Salah", "54. In Guezzam", "55. Touggourt",
                "56. Djanet", "57. El M'Ghair", "58. El Meniaa"
        };

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(wilayas));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_text_style, arrayList);
        wilayasSp.setAdapter(arrayAdapter);

        doneBtnTv.setOnClickListener(v -> {
            Intent intent = new Intent(EditPhoto.this, restaurantHome.class);
            startActivity(intent);
        });
    }

    protected void init() {
        restraunt_default_picture = findViewById(R.id.restraunt_default_picture);
        add_picture_icon = findViewById(R.id.add_picture_icon);
        wilayasSp = findViewById(R.id.wilayasSp);
        streetNameEt = findViewById(R.id.streetNameEt);
        doneBtnTv = findViewById(R.id.doneBtnTv);
    }
}
