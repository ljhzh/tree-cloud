package sdu.wocl.dataFactory.processor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdu.wocl.dataFactory.entity.Sentence;

public class SentenceProcessorFilter {
    
    private final static Logger logger = LoggerFactory.getLogger(SentenceProcessorFilter.class);
    
    public void processor(List<Sentence> sents) {
	for (Sentence sentence : sents) {
	    //logger.debug(sentence.getTextString());
	}
    }
    
}
