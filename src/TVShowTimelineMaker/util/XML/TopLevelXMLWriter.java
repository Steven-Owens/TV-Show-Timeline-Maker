/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.util.XML;

import TVShowTimelineMaker.Show;
import TVShowTimelineMaker.character.NamedCharacter;
import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import TVShowTimelineMaker.timeline.Episode;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.EventImp;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.timeline.OncePeriodEvent;
import TVShowTimelineMaker.timeline.Timeline;
import TVShowTimelineMaker.timeline.YearlyDayEvent;
import TVShowTimelineMaker.timeline.YearlyPeriodEvent;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.xml.sax.InputSource;

public class TopLevelXMLWriter {

    private static final Logger LOG = Logger.getLogger(TopLevelXMLWriter.class.getName());
    public static String defualtNameSpace = "Main";

    public static void writeXMLFile(String Path, Show inShow) throws IOException {
        org.jdom2.Document newDocument = createDocument(inShow);
        writeXMLFile(Path, newDocument);
        //writeXMLFile(new File(Path), inShow);
    }

    private static void writeXMLFile(String Path, org.jdom2.Document inDocument) throws IOException {
        writeXMLFile(new File(Path), inDocument);
    }

    public static void writeXMLFile(File inFile, Show inShow) throws IOException {
        org.jdom2.Document newDocument = createDocument(inShow);
        writeXMLFile(inFile, newDocument);
    }

    private static void writeXMLFile(File inFile, org.jdom2.Document inDocument) throws IOException {
        writeXMLFile(new java.io.BufferedOutputStream(new java.io.FileOutputStream(inFile)), inDocument);
    }

    public static void writeXMLFile(java.io.OutputStream inFile, Show inShow) throws IOException {
        org.jdom2.Document newDocument = createDocument(inShow);
        writeXMLFile(inFile, newDocument);
    }

    private static void writeXMLFile(java.io.OutputStream inFile, org.jdom2.Document inDocument) throws IOException {
        writeXMLFile(new java.io.BufferedOutputStream(inFile), inDocument);
    }

    public static void writeXMLFile(java.io.BufferedOutputStream inFile, Show inShow) throws IOException {
        org.jdom2.Document newDocument = createDocument(inShow);
        writeXMLFile(inFile, newDocument);
    }

    private static void writeXMLFile(java.io.BufferedOutputStream inFile, org.jdom2.Document inDocument) throws IOException {
        org.jdom2.output.XMLOutputter myXMLOutputter = new org.jdom2.output.XMLOutputter(org.jdom2.output.Format.getPrettyFormat());
        myXMLOutputter.output(inDocument, inFile);
    }

    public static void writeXMLFile(java.io.Writer inFile, Show inShow) throws IOException {
        org.jdom2.Document newDocument = createDocument(inShow);
        writeXMLFile(inFile, newDocument);
    }

    private static void writeXMLFile(java.io.Writer inFile, org.jdom2.Document inDocument) throws IOException {
        writeXMLFile(new java.io.BufferedWriter(inFile), inDocument);
    }

    public static void writeXMLFile(java.io.BufferedWriter inFile, Show inShow) throws IOException {
        org.jdom2.Document newDocument = createDocument(inShow);
        writeXMLFile(inFile, newDocument);
    }

    private static void writeXMLFile(java.io.BufferedWriter inFile, org.jdom2.Document inDocument) throws IOException {
        org.jdom2.output.XMLOutputter myXMLOutputter = new org.jdom2.output.XMLOutputter(org.jdom2.output.Format.getPrettyFormat());
        myXMLOutputter.output(inDocument, inFile);
    }

    @SuppressWarnings("unchecked")
    private static org.jdom2.Document createDocument(Show inShow) {
        Timeline inTimeline = inShow.getTimeLine();
        Element rootElement = new Element("MyLittlePony");
        rootElement.setAttribute("namespace", inShow.getNameSpace());
        Element EventsElement = new Element("Events");
        XMLWriter<OnceDayEvent> OnceDayEventXMLWriter = XMLWriterImp.getXMLWriter(OnceDayEvent.class);
        XMLWriter<OncePeriodEvent> OncePeriodEventXMLWriter = XMLWriterImp.getXMLWriter(OncePeriodEvent.class);
        XMLWriter<YearlyDayEvent> YearlyDayEventXMLWriter = XMLWriterImp.getXMLWriter(YearlyDayEvent.class);
        XMLWriter<YearlyPeriodEvent> YearlyPeriodEventXMLWriter = XMLWriterImp.getXMLWriter(YearlyPeriodEvent.class);
        EventsElement.addContent(inTimeline.getEvents().parallelStream()
                .filter((Event curEvent) -> curEvent.getRootNameSpace().equals(inShow.getNameSpace()))
                .map((Event curEvent) -> {
                    Element newElement = null;
                    if (curEvent instanceof OnceDayEvent) {
                        newElement = OnceDayEventXMLWriter.writeElements((OnceDayEvent) curEvent);
                    } else if (curEvent instanceof OncePeriodEvent) {
                        newElement = OncePeriodEventXMLWriter.writeElements((OncePeriodEvent) curEvent);
                    } else if (curEvent instanceof YearlyDayEvent) {
                        newElement = YearlyDayEventXMLWriter.writeElements((YearlyDayEvent) curEvent);
                    } else if (curEvent instanceof YearlyPeriodEvent) {
                        newElement = YearlyPeriodEventXMLWriter.writeElements((YearlyPeriodEvent) curEvent);
                    }
                    return newElement;
                }).collect(Collectors.toList()));
        rootElement.addContent(EventsElement);
        Element CharactersElement = new Element("Characters");
        XMLWriter<NamedCharacter> NamedCharacterXMLWriter = XMLWriterImp.getXMLWriter(NamedCharacter.class);
        CharactersElement.addContent(inShow.getCharacters().parallelStream().map(NamedCharacterXMLWriter::writeElements).collect(Collectors.toList()));
        rootElement.addContent(CharactersElement);
        Element EpisodesElement = new Element("Episodes");
        XMLWriter<Episode> EpisodeXMLWriter = XMLWriterImp.getXMLWriter(Episode.class);
        //todo: make mutithreaded
        EpisodesElement.addContent(inShow.getEpisodes().parallelStream().map(EpisodeXMLWriter::writeElements).collect(Collectors.toList()));
        rootElement.addContent(EpisodesElement);
        Element ConstraintsElement = new Element("Constraints");
        ConstraintsElement.addContent(inTimeline.getConstraints().parallelStream().filter((TimeConstraint t) -> !t.isSynthetic()).filter((TimeConstraint t) -> XMLWriterImp.getXMLWriter(t.getClass()) != null).map((TimeConstraint curTimeConstraint) -> {
            XMLWriter ConstraintXMLWriter = XMLWriterImp.getXMLWriter(curTimeConstraint.getClass());
            return ConstraintXMLWriter.writeElements(curTimeConstraint);
        }).collect(Collectors.toList()));
        rootElement.addContent(ConstraintsElement);
        org.jdom2.Document newDocument = new org.jdom2.Document(rootElement);
        return newDocument;
    }

    public static Show readXMLFile(String Path) throws JDOMException, IOException {
        return readXMLFile(new File(Path));
    }

    public static Show readXMLFile(File inFile) throws JDOMException, IOException {
        org.jdom2.input.SAXBuilder newSAXBuilder = new org.jdom2.input.SAXBuilder();
        String fileName = inFile.getName();
        defualtNameSpace = fileName.substring(0, fileName.lastIndexOf('.'));
        Document ShowDoc = newSAXBuilder.build(inFile);
        return createShowFromDocument(ShowDoc);
    }

    public static Show readXMLFile(java.io.InputStream inFile) throws JDOMException, IOException {
        return readXMLFile(new java.io.BufferedInputStream(inFile));
    }

    public static Show readXMLFile(java.io.BufferedInputStream inFile) throws JDOMException, IOException {
        org.jdom2.input.SAXBuilder newSAXBuilder = new org.jdom2.input.SAXBuilder();
        Document ShowDoc = newSAXBuilder.build(inFile);
        return createShowFromDocument(ShowDoc);
    }

    public static Show readXMLFile(java.io.Reader inFile) throws JDOMException, IOException {
        return readXMLFile(new java.io.BufferedReader(inFile));
    }

    public static Show readXMLFile(java.io.BufferedReader inFile) throws JDOMException, IOException {
        org.jdom2.input.SAXBuilder newSAXBuilder = new org.jdom2.input.SAXBuilder();
        Document ShowDoc = newSAXBuilder.build(inFile);
        return createShowFromDocument(ShowDoc);
    }

    public static Show readXMLFile(InputSource inFile) throws JDOMException, IOException {
        org.jdom2.input.SAXBuilder newSAXBuilder = new org.jdom2.input.SAXBuilder();
        Document ShowDoc = newSAXBuilder.build(inFile);
        return createShowFromDocument(ShowDoc);
    }

    // </editor-fold>
    private static Show createShowFromDocument(Document inShowDocument) {
        Element rootElement = inShowDocument.getRootElement();
        Attribute namespaceAttribute = rootElement.getAttribute("namespace");
        Show newShow;
        if (namespaceAttribute != null) {
            newShow = new Show(namespaceAttribute.getValue());
        } else {
            String baseURI = inShowDocument.getBaseURI();
            int beginIndex = baseURI.lastIndexOf('/');
            int endIndex = baseURI.lastIndexOf('.');
            if (beginIndex < 0){
                beginIndex = 1;
            } else {
               beginIndex += 1; 
            }
            if ((endIndex < 0) || (endIndex <= beginIndex)){
                endIndex = baseURI.length();
            }
            baseURI = baseURI.substring(beginIndex, endIndex).replaceAll("%20", " ");
            if (baseURI != null) {
                newShow = new Show(baseURI);
            } else {
                newShow = new Show(defualtNameSpace);
            }
        }
        defualtNameSpace = newShow.getNameSpace();
        Timeline newTimeline = newShow.getTimeLine();
        Element EventsElement = rootElement.getChild("Events");
        //todo: make mutithreaded
        List<Event> collectedEvents = EventsElement.getChildren().parallelStream()
                .filter((Element curEventElement) -> XMLWriterImp.getXMLWriter(MyLittePonyMaps.getEventClassForFriendlyString(curEventElement.getName())) != null)
                .map((Element curEventElement) -> {
                    XMLWriter EventXMLWriter = XMLWriterImp.getXMLWriter(MyLittePonyMaps.getEventClassForFriendlyString(curEventElement.getName()));
                    return EventXMLWriter.readElements(curEventElement);
                }).filter((Object o) -> o instanceof Event).map((Object o) -> (Event) o).collect(Collectors.toList());
        newTimeline.addEvents(collectedEvents);
        EventImp.addEventsToEventMap(collectedEvents);
        Element CharactersElement = rootElement.getChild("Characters");
        XMLWriter<NamedCharacter> NamedCharacterXMLWriter = XMLWriterImp.getXMLWriter(NamedCharacter.class);
        List<NamedCharacter> collectedCharacters = CharactersElement.getChildren().parallelStream().map(NamedCharacterXMLWriter::readElements).collect(Collectors.toList());
        newShow.addCharacters(collectedCharacters);
        NamedCharacter.addCharactersIntoCharacterMap(collectedCharacters);
        Element EpisodesElement = rootElement.getChild("Episodes");
        XMLWriter<Episode> EpisodeXMLWriter = XMLWriterImp.getXMLWriter(Episode.class);
        //todo: make mutithreaded
        List<Episode> collectedEpisodes = EpisodesElement.getChildren().parallelStream().map(EpisodeXMLWriter::readElements).collect(Collectors.toList());
        newShow.addEpisodes(collectedEpisodes);
        Element ConstraintsElement = rootElement.getChild("Constraints");
        //todo: make mutithreaded
        newTimeline.addTimeConstraints(ConstraintsElement.getChildren().parallelStream()
                .filter((Element curElement) -> XMLWriterImp.getXMLWriter(MyLittePonyMaps.getConstraintClassForFriendlyString(curElement.getName())) != null)
                .map((Element curElement) -> {
                    XMLWriter ConstraintXMLWriter = XMLWriterImp.getXMLWriter(MyLittePonyMaps.getConstraintClassForFriendlyString(curElement.getName()));
                    return (TimeConstraint) ConstraintXMLWriter.readElements(curElement);
                }).collect(Collectors.toList()));
        return newShow;
    }

    private File mFile;

    public TopLevelXMLWriter(String Path) {
        this(new File(Path));
    }

    public TopLevelXMLWriter(File inFile) {
        this.mFile = inFile;
    }

    public void writeXMLFile(Show inShow) throws IOException {
        writeXMLFile(this.mFile, inShow);
    }

    // <editor-fold defaultstate="collapsed" desc="readXMLFile methods">
    public Show readXMLFile() throws JDOMException, IOException {
        return readXMLFile(this.mFile);
    }
}
