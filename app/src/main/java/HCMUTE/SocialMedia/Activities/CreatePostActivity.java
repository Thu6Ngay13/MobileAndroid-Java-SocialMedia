package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;
import HCMUTE.SocialMedia.Adapters.CreatePostAdapter;
import HCMUTE.SocialMedia.R;

public class CreatePostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String[] modeName={"Public","Private"};
    int modeImage[] = {R.mipmap.ic_global_72_dark, R.mipmap.ic_lock_72_dark};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.spModePost);
        spin.setOnItemSelectedListener(this);

        CreatePostAdapter createPostAdapter=new CreatePostAdapter(getApplicationContext(),modeImage,modeName);
        spin.setAdapter(createPostAdapter);
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        Toast.makeText(getApplicationContext(), modeName[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}