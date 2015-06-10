package memoryheroeses.menglin9.washington.edu.memoryheroes;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Menglin on 5/23/15.
 */
public class memoryApp extends Application {

    //using for creating singlinton
    private static memoryApp sInstance = null;
    ArrayList<Category> categories;
    public String appName = "";
    public int position = 0;
    public int flashCardIndex = 0;
    public int score = 0;
    public ArrayList<String> fourChoice;
    Set<String> unique4list;
    boolean checked = false;
    ArrayList<String> al;
    ArrayList<FlashCard> intelCard;
    boolean intelM = false;
    //public int correctIndex;

    // JSON Node names
    private static final String TAG_appName = "app_name";
    private static final String TAG_category = "category";
    private static final String TAG_content = "content";
    private static final String TAG_front = "front";
    private static final String TAG_back = "back";
    private static final String TAG_weight = "weight";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("app", "memoryApp has been loaded and run!!!");
        //get json data
        String json = null;

       /*
        try {
            InputStream inputStream = getAssets().open("data.json");
            json = readJSONFile(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        File myFile = new File(getFilesDir().getAbsolutePath(), "/download.json");  // this string is where you can specify what file you are looking for inside your data/ directory

        // Let's get the JSON in the files directory! (aka data/data.json which is a hidden folder that you can't access or see unless its from the app itself)
        // check if data.json file exists in files directory
        if (myFile.exists()) {
            Log.i("MyApp", "data.json DOES exist");

            try {
                FileInputStream fis = openFileInput("download.json");      // sweet we found it. openFileInput() takes a string path from your data directory. no need to put 'data/' in your path parameter
                json = readJSONFile(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            // Can't find data.json file in files directory. Fetch data.json in assets
            Log.i("MyApp", "data.json DOESN'T exist. Fetch from assets");

            try {
                InputStream inputStream = getAssets().open("data.json");
                json = readJSONFile(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.i("json", "" + json);


        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject o1 = jsonArray.getJSONObject(0);
            appName = o1.getString(TAG_appName);
            for (int i = 1; i < jsonArray.length(); i++) {
                JSONObject o1N = jsonArray.getJSONObject(i);
                Category category = new Category();
                category.setName(o1N.getString(TAG_category));
                JSONArray flashCard = o1N.getJSONArray(TAG_content);
                for (int j = 0; j < flashCard.length(); j++) {
                    JSONObject o2N = flashCard.getJSONObject(j);
                    String front = o2N.getString(TAG_front);
                    String back = o2N.getString(TAG_back);
                    String weightS = o2N.getString(TAG_weight);
                    int weight = Integer.parseInt(weightS);

                    FlashCard flashC = new FlashCard();
                    flashC.setFront(front);
                    flashC.setBack(back);
                    flashC.setWeight(weight);
                    category.getFlashCard().add(flashC);
                    category.getChoices().add(back);
                }
                categories.add(category);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // reads InputStream of JSON file and returns the file in JSON String format
    public String readJSONFile(InputStream inputStream) throws IOException {

        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();

        return new String(buffer, "UTF-8");
    }

    public String readJSONFile(FileInputStream fileInputStream) throws IOException {
        // grab code from previous assignment. I'm pretty sure i posted this method in another repository
        int size = fileInputStream.available();
        byte[] buffer = new byte[size];
        fileInputStream.read(buffer);
        fileInputStream.close();

        return new String(buffer, "UTF-8");
    }

    public memoryApp() {
        if (sInstance == null) {
            sInstance = this;
        } else {
            throw new RuntimeException("cannot create more than one memoryApp! ");
        }

        categories = new ArrayList<Category>();

        //topicList = new ArrayList<Topic>();
    }

    public String getAppName() {
        return appName;
    }

    public String[] getAllCategory() {
        int length = categories.size();
        String[] topics = new String[length];
        for (int i = 0; i < length; i++) {
            topics[i] = categories.get(i).getName();
        }
        return topics;
    }

    public ArrayList<String> getAllbacks() {
        int length = categories.size();
        ArrayList<String> allBacks = new ArrayList<String>();
        for (int i = 0; i < length; i++) {
            int length_falshCard = categories.get(i).getFlashCard().size();
            for (int j = 0; j < length_falshCard; j++ ) {
                allBacks.add(categories.get(i).getFlashCard().get(j).getBack());
            }
        }
        return allBacks;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return  position;
    }

    public void setFlashCardIndex(int flashCardIndex) {
        this.flashCardIndex = flashCardIndex;
    }

    public int getFlashCardIndex() {
        return  flashCardIndex;
    }

    public String getCurrentCategory() {
        return categories.get(position).getName();
    }

    public String getCurrentFront() {
        return categories.get(position).getFlashCard().get(flashCardIndex).getFront();
    }

    public String getCurrentBack() {
        return categories.get(position).getFlashCard().get(flashCardIndex).getBack();
    }

    public int getCurrentWeight() {
        return categories.get(position).getFlashCard().get(flashCardIndex).getWeight();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return  score;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean getChecked() {
        return checked;
    }

    public int getFlashCardNum() {
        return categories.get(position).getFlashCard().size();
    }

    //get arraylist of answers for certain location
    public ArrayList<String> getAllChoices(int position) {
        return categories.get(position).getChoices();
    }

    //get four answers from certain category
    public ArrayList<String> getFourChoice(int position) {
        fourChoice = new ArrayList<String>();
        //ArrayList<String> list = new ArrayList<String>();

        int answersSize = getAllChoices(position).size();
        Random rand = new Random();
        for (int i = 0; i < 3; i++){
            int randomNumber = rand.nextInt(answersSize);
            fourChoice.add(getAllChoices(position).get(randomNumber));
            fourChoice.add(getCurrentBack());
        }
        Collections.shuffle(fourChoice);
        return fourChoice;
    }

    //get four unique answers from certain category
    public Set<String> getUniqueChoices(int position) {
        unique4list = new TreeSet<>();
        unique4list.clear();
        unique4list.add(getCurrentBack());
        int answersSize = getAllChoices(position).size();
        Random rand = new Random();
        while (unique4list.size() != 4) {
            int randomNumber = rand.nextInt(answersSize);
            String select = getAllChoices(position).get(randomNumber);
            if (!unique4list.contains(select)) {
                unique4list.add(select);
                String test = unique4list.toString();
                Iterator<String> itr=unique4list.iterator();
                al = new ArrayList<String>();
                al.clear();
                while(itr.hasNext()){
                    String c=itr.next();
                    al.add(c);
                }
                Log.i("hihi", "" + test);
            }
        }
        return unique4list;
    }

    public ArrayList<String> getAl() {
        getUniqueChoices(position);
        return al;
    }

    public boolean getIntelM() {
        return intelM;
    }

    public void setIntelM(boolean M) {
        this.intelM = M;
    }

    //get correct index of answers from a shuffled arraylist
    public int getCorrectIndex() {
        if (fourChoice != null) {
            for (int i = 0; i < fourChoice.size(); i++) {
                if (fourChoice.get(i).equals(getCurrentBack())) {
                    return i;
                }
            }
        } else {
            throw new IllegalStateException("Choices Object not inititialized");
        }
        return -1;
    }

    public ArrayList<FlashCard> makeICards() {
        intelCard = categories.get(position).getFlashCard();
        Collections.sort(intelCard);
        return intelCard;
    }

    public String getIfront() {
        return intelCard.get(flashCardIndex).getFront();
    }


    public String getIback() {
        return intelCard.get(flashCardIndex).getBack();
    }

    public FlashCard getCurrentFlashCard() {
        return categories.get(position).getFlashCard().get(flashCardIndex);
    }
}
