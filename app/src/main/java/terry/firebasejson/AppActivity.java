package terry.firebasejson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AppActivity extends AppCompatActivity {

    String personRef = "Person";
    String firstNameRef = "FirstName";
    String lastNameRef = "LastName";
    String genderRef = "Gender";

    // Define Firebase
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference fireFirstName = rootRef.child(personRef).child(firstNameRef);
    DatabaseReference fireLastName = rootRef.child(personRef).child(lastNameRef);
    DatabaseReference fireGender = rootRef.child(personRef).child(genderRef);


    // Structure of the JSON
    private ArrayList<String> mKeys = new ArrayList<>();
    private ArrayList<String> mFirstName = new ArrayList<>();

    // UI
    TextView mFirstNameView;
    TextView mLastNameView;
    TextView mGenderView;
    ListView listView;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appactivity);

        mFirstNameView = findViewById(R.id.firstNameView);
        mLastNameView = findViewById(R.id.lastNameView);
        mGenderView = findViewById(R.id.genderView);
        listView = (ListView) findViewById(R.id.listView);
        mButton = findViewById(R.id.updateButton);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mFirstName);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {


                String selectedKey = mKeys.get(position);

                // Retrieve First Name from the Firebase
                DatabaseReference cardFirstName = fireFirstName.child(selectedKey);
                cardFirstName.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String text = dataSnapshot.getValue().toString();
                        mFirstNameView.setText(text);
                        Toast toast = Toast.makeText(AppActivity.this, text, Toast.LENGTH_LONG);
                        toast.show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                // Retrieve First Name from the Firebase
                DatabaseReference cardLastName = fireLastName.child(selectedKey);
                cardLastName.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String text = dataSnapshot.getValue().toString();
                        mLastNameView.setText(text);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                // Retrieve First Name from the Firebase
                DatabaseReference cardGender = fireGender.child(selectedKey);
                cardGender.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String text = dataSnapshot.getValue().toString();
                        mGenderView.setText(text);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }
        });



        fireFirstName.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String message = dataSnapshot.getValue().toString();
                mFirstName.add(message);

                String key = dataSnapshot.getKey();
                mKeys.add(key);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String message = dataSnapshot.getValue().toString();
                String key = dataSnapshot.getKey();

                int index = mKeys.indexOf(key);

                mFirstName.set(index, message);

                adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue().toString();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
