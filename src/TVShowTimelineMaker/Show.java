/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker;

import TVShowTimelineMaker.character.NamedCharacter;
import TVShowTimelineMaker.timeline.Episode;
import TVShowTimelineMaker.timeline.Timeline;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Steven Owens
 */
public class Show {
    private static final Logger LOG = Logger.getLogger(Show.class.getName());
    private final Collection<Episode> Episodes;
    private final Collection<NamedCharacter> Characters;
    private final Timeline mTimeLine;
    private final Collection<Episode> unmodifiableEpisodes;
    private final Collection<NamedCharacter> unmodifiableCharacters;
    private final String nameSpace;
    
    public Show(String nameSpace)
    {
        this.nameSpace = nameSpace;
        this.Episodes = new java.util.ArrayList<>();
        this.unmodifiableEpisodes = Collections.unmodifiableCollection(this.Episodes);
        this.mTimeLine = new Timeline();
        this.Characters = new java.util.ArrayList<>();
        this.unmodifiableCharacters = Collections.unmodifiableCollection(this.Characters);
    }
    
    public boolean addEpisode(Episode inEpisode) {
        return this.Episodes.add(inEpisode);
    }
    
    public void addEpisodes(List<Episode> collect) {
        this.Episodes.addAll(collect);
    }
    
    public boolean removeEpisode(Episode inEpisode) {
        return this.Episodes.remove(inEpisode);
    }
    
    public boolean addCharacter(NamedCharacter inCharacter) {
        boolean succ = this.mTimeLine.addEvent(inCharacter.getBirthday());
        succ =  this.Characters.add(inCharacter) && succ;
        return succ;
    }
    
    public void addCharacters(List<NamedCharacter> collect) {
        this.Characters.addAll(collect);
        this.mTimeLine.addEvents(collect.parallelStream().map((NamedCharacter curCharacter) -> curCharacter.getBirthday()).collect(Collectors.toList()));
    }
    
    public boolean removeCharacter(NamedCharacter inCharacter) {
        return this.Characters.remove(inCharacter);
    }

    /**
     * @return the Episodes
     */
    public Collection<Episode> getEpisodes() {
        return this.unmodifiableEpisodes;
    }

    /**
     * @return the Characters
     */
    public Collection<NamedCharacter> getCharacters() {
        return this.unmodifiableCharacters;
    }

    /**
     * @return the mTimeLine
     */
    public Timeline getTimeLine() {
        return this.mTimeLine;
    }

    /**
     * @return the nameSpace
     */
    public String getNameSpace() {
        return nameSpace;
    }

    

    
}
