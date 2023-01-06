package capers;

import java.io.*;

/** Represents a dog that can be serialized.
 * @author TODO
*/
public class Dog implements Serializable{ // TODO

    /** Folder that dogs live in. */
    static final File DOG_FOLDER = Utils.join(CapersRepository.CWD,".capers", "dogs"); // TODO (hint: look at the `join`
                                         //      function in Utils)

    /** Age of dog. */
    private int age;
    /** Breed of dog. */
    private String breed;
    /** Name of dog. */
    private String name;

    /**
     * Creates a dog object with the specified parameters.
     * @param name Name of dog
     * @param breed Breed of dog
     * @param age Age of dog
     */
    public Dog(String name, String breed, int age) {
        this.age = age;
        this.breed = breed;
        this.name = name;
    }

    /**
     * Reads in and deserializes a dog from a file with name NAME in DOG_FOLDER.
     *
     * @param name Name of dog to load
     * @return Dog read from file
     */
    public static Dog fromFile(String name) {
        // TODO (hint: look at the Utils file)
        File inFile = Utils.join(DOG_FOLDER, name);
        if (!inFile.exists()) return null;

        Dog dog = Utils.readObject(inFile, Dog.class);
        return dog;
    }

    /**
     * Increases a dog's age and celebrates!
     */
    public void haveBirthday() {
        age += 1;
        System.out.println(toString());
        System.out.println("Happy birthday! Woof! Woof!");
    }

    /**
     * Saves a dog to a file for future use.
     * example:
     *     Model m;
     *     File inFile = new File(saveFileName);
     *     try {
     *         ObjectInputStream inp =
     *             new ObjectInputStream(new FileInputStream(inFile));
     *         m = (Model) inp.readObject();
     *         inp.close();
     *     } catch (IOException | ClassNotFoundException excp) {
     *         ...
     *         m = null;
     *     }
     */
    public void saveDog() throws IOException {
        // TODO (hint: don't forget dog names are unique)
/*
        boolean isDup = false;
        String[] strings DOG_FOLDER.list());
        for (String s : strings) {
            if (s.equals(name)) {
                isDup = true;
                break;
            }
        }

        if (isDup)  return;
*/
        File infile = Utils.join(DOG_FOLDER, name);

        infile.createNewFile();
        Utils.writeObject(infile, this);
    }

    @Override
    public String toString() {
        return String.format(
            "Woof! My name is %s and I am a %s! I am %d years old! Woof!",
            name, breed, age);
    }

}
