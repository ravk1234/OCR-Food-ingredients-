package com.example.smartfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class TextParser  {

    public ArrayList<ArrayList<String>> allAllergens;
    public ArrayList<String> userAllergens = new ArrayList<>();
    public ArrayList<String> allLactose; // the list of things a lactose intolerant person should not eat
    public ArrayList<String> allVegan;
    public ArrayList<String> allVegetarian;
    public ArrayList<String> allGluten;

    public TextParser() {
        this.allAllergens = this.fillInAllergens();
        this.allLactose = (new ArrayList(Arrays.asList("milk", "butter", "buttermilk", "casein", "cheese", "cream",
                "curds", "lactose", "lactulose", "lactate", "custard", "yogurt")));
        this.allVegan = (new ArrayList(Arrays.asList("milk", "butter", "buttermilk", "casein", "cheese", "cream",
                "curds", "lactose", "lactulose", "lactate", "custard", "yogurt", "pork", "meat", "beef", "chicken",
                "honey", "lamb", "veal", "turkey", "egg", "anchovies", "bass", "catfish", "cod", "grouper", "haddock",
                "pike", "salmon", "snapper", "tilapia", "tuna", "trout", "fish", "crawfish", "crab", "krill",
                "lobster", "shrimp", "mussels", "squid", "ham", "bacon", "gelatin")));
        this.allVegetarian = (new ArrayList(Arrays.asList("pork", "meat", "beef", "chicken",
                "lamb", "veal", "turkey", "egg", "anchovies", "bass", "catfish", "cod", "grouper", "haddock",
                "pike", "salmon", "snapper", "tilapia", "tuna", "trout", "fish", "crawfish", "crab", "krill",
                "lobster", "shrimp", "mussels", "squid", "ham", "bacon")));
        this.allGluten = (new ArrayList(Arrays.asList("wheat", "rye", "barley", "bulgur", "couscous", "farina", "flour")));
    }

    public static void main(String[] args) {
        TextParser tt = new TextParser();

        tt.setUserPreferences("1111111111");
        ArrayList<String> ii = new ArrayList<String>(Arrays.asList(" hoiusdfhium,, oifdshj hif  1.Gelatingt"));
        ArrayList<ArrayList> done = tt.checkAllergens(ii);
        ArrayList<String> done2 = tt.checkLactose(ii);
        ArrayList<String> done3 = tt.checkVegan(ii);


        for (ArrayList<String> sub : done) {
            for (String str : sub) {
                System.out.println(str);
            }
        }

        for (String str : done2) {
            System.out.println(str);
        }

        for (String str : done3) {
            System.out.println(str);
        }

    }

    public int getUserBmr(int mass, int age, int height) {
        double menBmr = 66.473 + (13.7516 * mass) + (5.0033 * height) - (6.755 * age);
        double womenBmr = 655.0955 + (9.5634 * mass) + (1.8496 * height) + (4.6756 * age);
        int result = (int) (menBmr + womenBmr) / 2;
        return result;
    }

    public ArrayList checkNutrition(ArrayList<String> nutritionFactsInput, String mass, String age, String height) {
        ArrayList nutritionFacts = this.processInput(nutritionFactsInput);
        ArrayList returnList = new ArrayList();
        String returnString = new String();
        int userMass;
        int userAge;
        int userHeight;
        try {
            userMass = Integer.parseInt(mass);
            userAge = Integer.parseInt(age);
            userHeight = Integer.parseInt(height);
        } catch (Exception e) {
            returnList.add("The weight, age and/or height for the user is/are invalid");
            returnString += "The weight, age or height for the user is invalid";
            return returnList;
        }
        if (nutritionFacts.contains("calories")) {
            try {
                int calories = Integer.valueOf((nutritionFacts.indexOf("calories") + 1));
                int userBmr = getUserBmr(userMass, userAge, userHeight);
                int percent_cal = (int) ((userBmr / calories) * 100);
                if (calories > userBmr) {
                    returnList.add("The calorie count in this food is over the daily recommended minimum for you!");
                    returnString += " The calorie count in this food is over the daily recommended minimum for you!";
                } else if (calories == userBmr) {
                    returnList.add("The calorie count in this food is at the daily recommended minimum for you!");
                    returnString += " The calorie count in this food is at the daily recommended minimum for you!";
                } else {
                    returnList.add("The calorie count in this food is " + percent_cal + "percent the daily recommended minimum for you");
                    returnString += " The calorie count in this food is " + percent_cal + "percent the daily recommended minimum for you";
                }
            } catch (Exception e) {
                returnList.add("Calorie related data could not be calculated");
                returnString += " Calorie related data could not be calculated";
            }
        }
        if (nutritionFacts.contains("sodium")) {
            try {
                int sodium_mass = Integer.valueOf(nutritionFacts.indexOf("sodium") + 1);
                int percent_sodium = ((sodium_mass / 2300) * 100);
                if (percent_sodium > 100) {
                    returnList.add("The sodium content in this food is over the daily recommended limit of 2300 miligrams !");
                    returnString += " The sodium content in this food is over the daily recommended limit of 2300 miligrams !";
                } else if (percent_sodium == 100) {
                    returnList.add("The sodium content in this food is at the daily recommended limit of 2300 miligrams !");
                    returnString += " The sodium content in this food is at the daily recommended limit of 2300 miligrams !";
                } else {
                    returnList.add("The sodium content in this food is " + percent_sodium + "percent the daily recommended limit of 2300 miligrams");
                    returnString += "The sodium content in this food is " + percent_sodium + "percent the daily recommended limit of 2300 miligrams";
                }
            } catch (Exception e) {
                returnList.add("Sodium related data could not be calculated");
                returnString += " Sodium related data could not be calculated";
            }

        }
        if (returnString.length() > 0) {
            returnList.add(returnString);
        }
        return returnList;
    }

    public ArrayList fillInAllergens() {
        ArrayList returnList = new ArrayList();
        // milk allergens
        returnList.add(new ArrayList(Arrays.asList("milk", "butter", "buttermilk", "casein", "cheese", "cream",
                "curds", "lactose", "lactulose", "lactate", "custard", "yogurt")));
        // egg allergens
        returnList.add(new ArrayList(Arrays.asList("globulin", "egg", "eggs", "eggnog", "lysozyme", "albumin")));
        // peanut/nut allergens
        returnList.add(new ArrayList(Arrays.asList("peanut", "nuts", "nut", "almond",
                "beechnut", "walnut", "butternut", "cashew",
                "chestnut", "pecan", "pistachio")));
        // wheat allergens
        returnList.add(new ArrayList(Arrays.asList("wheat", "bread", "bulgur", "cereal", "cracker", "flour")));
        // soy allergens
        returnList.add(new ArrayList(Arrays.asList("soy", "soya", "miso", "tofu", "edamame")));
        // seafood allergens
        returnList.add(new ArrayList(Arrays.asList("anchovies", "bass", "catfish", "cod", "grouper", "haddock",
                "pike", "salmon", "snapper", "tilapia", "tuna", "trout", "fish", "crawfish", "crab", "krill",
                "lobster", "shrimp", "mussels", "squid")));
        return returnList;
    }

    public ArrayList processInput(ArrayList<String> ingredients) {
        ArrayList<String> allIngredients = new ArrayList();
        // turn ingredient list passed in into a single array called allIngredients
        String line;
        String[] linePieces;
        for (String str : ingredients) {
            line = str.toLowerCase();
            linePieces = line.split(" ");
            for (String str2 : linePieces) {
                allIngredients.add(str2.replaceAll("[^a-zA-Z]", ""));
            }
        }
        return allIngredients;
    }

    public ArrayList checkAllergens(ArrayList<String> ingredients) {
        ArrayList returnList = new ArrayList();
        String returnString = new String();
        ArrayList<String> allIngredients = this.processInput(ingredients);
        ArrayList mapping = new ArrayList(Arrays.asList("milk allergen(s)", "egg allergen(s)", "peanut/nut allergen(s)",
                "wheat allergen(s)", "soy allergen(s)", "seafood allergen(s)"));

        // now iterate and find any allergens
        ArrayList<String> temp;
        for (int index = 0; index < 6; index++) {
            if (this.userAllergens.get(index).equals("1")) {
                temp = new ArrayList();
                temp.add(("Possible " + mapping.get(index)));
                for (String allergen : this.allAllergens.get(index)) {
                    for (String ingredient : allIngredients) {
                        if (ingredient.contains(allergen) && temp.contains(allergen) == false) {
                            temp.add(allergen);
                        }
                    }
                }
                if (temp.size() > 1) {
                    for (String s : temp) {
                        returnString += s + " ";
                    }
                    returnList.add(temp);
                }

            }
        }
        if (returnString.length() > 0) {
            returnList.add(returnString);
        }
        return returnList;
    }

    public ArrayList checkLactose(ArrayList<String> ingredients) {
        // Return example ["The warning message here", "milk", "cheese", "lactose"]
        ArrayList returnList = new ArrayList();
        String returnString = new String();
        if (this.userAllergens.get(6).equals("1")) {
            //Log.i("Parse", "LACTOSE");
            returnList.add("Warning: Since you are lactose intolerant you may want to avoid eating this. It contains...");
            returnString += "Warning Since you are lactose intolerant you may want to avoid eating this. It contains";
            ArrayList<String> allIngredients = this.processInput(ingredients);
            for (String ingredient : allIngredients) {
                for (String item : this.allLactose) {
                    if (ingredient.contains(item)) {
                        returnList.add(item);
                        returnString += " " + item;
                    }
                }
            }
            if (returnList.size() <= 1) {
                returnList = new ArrayList();
                returnString = new String();
            }
            if (returnString.length() > 0) {
                returnList.add(returnString);
            }
        }
        return returnList;
    }

    public ArrayList checkVegan(ArrayList<String> ingredients) {
        // Return example ["The warning message here", "gluten", "pork", "beef"]
        ArrayList returnList = new ArrayList();
        String returnString = new String();
        if (this.userAllergens.get(7).equals("1")) {
            // Log.i("Parse", "VEGAN");
            returnList.add("Warning: Since you are a vegan you may want to avoid eating this. It contains...");
            returnString += "Warning Since you are a vegan you may want to avoid eating this. It contains";
            ArrayList<String> allIngredients = this.processInput(ingredients);
            for (String ingredient : allIngredients) {
                for (String item : this.allVegan) {
                    if (ingredient.contains(item)) {
                        returnList.add(item);
                        returnString += " " + item;
                    }
                }
            }
            if (returnList.size() <= 1) {
                returnList = new ArrayList();
                returnString = new String();
            }
        }
        if (returnString.length() > 0) {
            returnList.add(returnString);
        }
        return returnList;
    }

    public ArrayList checkVegaterian(ArrayList<String> ingredients) {
        // Return example ["The warning message here", "veal", "pork", "beef"]
        ArrayList returnList = new ArrayList();
        String returnString = new String();
        if (this.userAllergens.get(8).equals("1")) {
            // Log.i("Parse", "VEGETARIAN");
            returnList.add("Warning: Since you are a vegetarian you may want to avoid eating this. It contains...");
            returnString += "Warning Since you are a vegetarian you may want to avoid eating this. It contains";
            ArrayList<String> allIngredients = this.processInput(ingredients);
            for (String ingredient : allIngredients) {
                for (String item : this.allVegetarian) {
                    if (ingredient.contains(item)) {
                        returnList.add(item);
                        returnString += " " + item;
                    }
                }
            }
            if (returnList.size() <= 1) {
                returnList = new ArrayList();
                returnString = new String();
            }
        }
        if (returnString.length() > 0) {
            returnList.add(returnString);
        }
        return returnList;
    }

    public ArrayList checkGluten(ArrayList<String> ingredients) {
        // Return example ["The warning message here", "Gluten", "rye"]
        ArrayList returnList = new ArrayList();
        String returnString = new String();
        if (this.userAllergens.get(9).equals("1")) {
            // Log.i("Parse", "GLUTEN");
            returnList.add("Warning: Since you prefer gluten free foods you may want to avoid eating this. It contains...");
            returnString += "Warning Since you prefer gluten free foods you may want to avoid eating this. It contains";
            ArrayList<String> allIngredients = this.processInput(ingredients);
            for (String ingredient : allIngredients) {
                for (String item : this.allVegetarian) {
                    if (ingredient.contains(item)) {
                        returnList.add(item);
                        returnString += " " + item;
                    }
                }
            }
            if (returnList.size() <= 1) {
                returnList = new ArrayList();
                returnString = new String();
            }
        }
        if (returnString.length() > 0) {
            returnList.add(returnString);
        }
        return returnList;
    }

    public void setUserPreferences(String input) {
        this.userAllergens.clear();
        Log.i("cake", input);
        for (int i = 0; i < 10; i++) {
            this.userAllergens.add(Character.toString(input.charAt(i)));
        }
    }
}
