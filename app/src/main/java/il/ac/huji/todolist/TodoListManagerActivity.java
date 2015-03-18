package il.ac.huji.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class TodoListManagerActivity extends ActionBarActivity {
    private ItemsAdapter<String> adapter;
    private ArrayList<String> items;
    public class ItemsAdapter<String> extends ArrayAdapter<String> {
        public ItemsAdapter(Context context, int resource, List<String> list) {
            super(context, resource, list);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView)super.getView(position, convertView, parent);
            if (position % 2 == 0) {
                view.setBackgroundColor(Color.RED);
            }
            else {
                view.setBackgroundColor(Color.BLUE);
            }
            return view;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        items = new ArrayList<String>();
        adapter = new ItemsAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        ListView listOfItems =  (ListView)findViewById(R.id.lstTodoItems);
        listOfItems.setAdapter(adapter);
        listOfItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(TodoListManagerActivity.this);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setMessage(items.get(position));
                builder.setPositiveButton("Delete!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        items.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.show();
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuItemAdd) {
            TextView itemToAdd = (TextView)findViewById(R.id.edtNewItem);
            String stringToAdd = itemToAdd.getText().toString();
            itemToAdd.setText("");
            if(!stringToAdd.isEmpty()) {
                items.add(stringToAdd);
                adapter.notifyDataSetChanged();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
