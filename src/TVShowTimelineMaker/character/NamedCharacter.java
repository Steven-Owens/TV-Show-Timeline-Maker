/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.character;

import TVShowTimelineMaker.Main;
import TVShowTimelineMaker.timeline.EventImp;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.util.IDedObject;
import TVShowTimelineMaker.util.IDedObjectImp;
import TVShowTimelineMaker.util.IDedObjectImp.IDedObjectXMLWriter;
import TVShowTimelineMaker.util.XML.SubXMLWriter;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.jdom2.Element;

public class NamedCharacter extends IDedObjectImp
        implements Serializable, IDedObject {

    private static final long serialVersionUID = 3L;

    private static int count = 0;
    private final static Map<String, NamedCharacter> mCharacterMap = new java.util.HashMap<>(1);
    private static final Logger LOG = Logger.getLogger(NamedCharacter.class.getName());

    public static void addCharacterIntoCharacterMap(NamedCharacter inNamedCharacter) {
        mCharacterMap.put(inNamedCharacter.getID(), inNamedCharacter);
        mCharacterMap.put(java.lang.Integer.toString(inNamedCharacter.getLeastSignificantIDPart()), inNamedCharacter);
    }

    public static void addCharactersIntoCharacterMap(Collection<NamedCharacter> inNamedCharacters) {
        mCharacterMap.putAll(inNamedCharacters.parallelStream().collect(HashMap<String, NamedCharacter>::new, (HashMap<String, NamedCharacter> t, NamedCharacter curNamedCharacter) -> {
            t.put(curNamedCharacter.getID(), curNamedCharacter);
            t.put(java.lang.Integer.toString(curNamedCharacter.getLeastSignificantIDPart()), curNamedCharacter);
        }, HashMap<String, NamedCharacter>::putAll));
    }

    public static NamedCharacter getCharacterByID(String ID) {
        return mCharacterMap.get(ID);
    }

    private String name;
    private final OnceDayEvent birthday;

    public NamedCharacter() {
        this("new Character");
    }

    public NamedCharacter(String inName) {
        this(inName, new OnceDayEvent(inName + " was born"));
    }

    private NamedCharacter(String inName, OnceDayEvent inBirthday) {
        super(Main.myShow.getNameSpace(), "NamedCharacter", count);
        count++;
        this.name = inName;
        this.birthday = inBirthday;
    }

    private NamedCharacter(Element root) {
        super(root, "NamedCharacter");
        EventImp.EventIDXMLWriter appEventIDXMLWriter = EventImp.EventIDXMLWriter.instance;
        Element nameElement = root.getChild("name");
        this.name = nameElement.getTextNormalize();
        if (this.getLeastSignificantIDPart() >= count) {
            count = this.getLeastSignificantIDPart() + 1;
        }
        Element birthdayElement = root.getChild("birthday");
        this.birthday = (OnceDayEvent) appEventIDXMLWriter.readElements(birthdayElement);
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the birthday
     */
    public OnceDayEvent getBirthday() {
        return this.birthday;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
        this.birthday.setName(name + " was born");
    }

    public static class NamedCharacterIDXMLWriter extends XMLWriterImp<NamedCharacter> implements SubXMLWriter<NamedCharacter> {

        @Override
        public Element writeElements(NamedCharacter ObjectToWrite) {
            Element newRoot = new Element("CharacterByID");
            writeElements(ObjectToWrite, newRoot);
            return newRoot;
        }

        @Override
        public void writeElements(NamedCharacter ObjectToWrite, Element newRoot) {
            newRoot.setText(ObjectToWrite.getID());
        }

        @Override
        public NamedCharacter readElements(Element root) {
            return NamedCharacter.getCharacterByID(root.getTextNormalize());
        }

        public final static NamedCharacterIDXMLWriter instance = new NamedCharacterIDXMLWriter();

    }

    public static class NamedCharacterXMLWriter
            extends IDedObjectXMLWriter<NamedCharacter> {

        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(NamedCharacter.class, new NamedCharacterXMLWriter());
        }

        @Override
        public Element writeElements(NamedCharacter ObjectToWrite) {
            EventImp.EventIDXMLWriter appEventIDXMLWriter = EventImp.EventIDXMLWriter.instance;
            Element newElement = new Element("NamedCharacter");
            super.writeElements(ObjectToWrite, newElement);
            Element nameElement = new Element("name");
            nameElement.setText(ObjectToWrite.name);
            newElement.addContent(nameElement);
            Element birthdayElement = new Element("birthday");
            appEventIDXMLWriter.writeElements(ObjectToWrite.getBirthday(), birthdayElement);
            newElement.addContent(birthdayElement);
            return newElement;
        }

        @Override
        public NamedCharacter readElements(Element root) {
            return new NamedCharacter(root);
        }
    }
}
