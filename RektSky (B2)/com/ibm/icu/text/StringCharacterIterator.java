package com.ibm.icu.text;

import java.text.*;
import com.ibm.icu.util.*;

@Deprecated
public final class StringCharacterIterator implements CharacterIterator
{
    private String text;
    private int begin;
    private int end;
    private int pos;
    
    @Deprecated
    public StringCharacterIterator(final String text) {
        this(text, 0);
    }
    
    @Deprecated
    public StringCharacterIterator(final String text, final int pos) {
        this(text, 0, text.length(), pos);
    }
    
    @Deprecated
    public StringCharacterIterator(final String text, final int begin, final int end, final int pos) {
        if (text == null) {
            throw new NullPointerException();
        }
        this.text = text;
        if (begin < 0 || begin > end || end > text.length()) {
            throw new IllegalArgumentException("Invalid substring range");
        }
        if (pos < begin || pos > end) {
            throw new IllegalArgumentException("Invalid position");
        }
        this.begin = begin;
        this.end = end;
        this.pos = pos;
    }
    
    @Deprecated
    public void setText(final String text) {
        if (text == null) {
            throw new NullPointerException();
        }
        this.text = text;
        this.begin = 0;
        this.end = text.length();
        this.pos = 0;
    }
    
    @Deprecated
    @Override
    public char first() {
        this.pos = this.begin;
        return this.current();
    }
    
    @Deprecated
    @Override
    public char last() {
        if (this.end != this.begin) {
            this.pos = this.end - 1;
        }
        else {
            this.pos = this.end;
        }
        return this.current();
    }
    
    @Deprecated
    @Override
    public char setIndex(final int p) {
        if (p < this.begin || p > this.end) {
            throw new IllegalArgumentException("Invalid index");
        }
        this.pos = p;
        return this.current();
    }
    
    @Deprecated
    @Override
    public char current() {
        if (this.pos >= this.begin && this.pos < this.end) {
            return this.text.charAt(this.pos);
        }
        return '\uffff';
    }
    
    @Deprecated
    @Override
    public char next() {
        if (this.pos < this.end - 1) {
            ++this.pos;
            return this.text.charAt(this.pos);
        }
        this.pos = this.end;
        return '\uffff';
    }
    
    @Deprecated
    @Override
    public char previous() {
        if (this.pos > this.begin) {
            --this.pos;
            return this.text.charAt(this.pos);
        }
        return '\uffff';
    }
    
    @Deprecated
    @Override
    public int getBeginIndex() {
        return this.begin;
    }
    
    @Deprecated
    @Override
    public int getEndIndex() {
        return this.end;
    }
    
    @Deprecated
    @Override
    public int getIndex() {
        return this.pos;
    }
    
    @Deprecated
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StringCharacterIterator)) {
            return false;
        }
        final StringCharacterIterator that = (StringCharacterIterator)obj;
        return this.hashCode() == that.hashCode() && this.text.equals(that.text) && this.pos == that.pos && this.begin == that.begin && this.end == that.end;
    }
    
    @Deprecated
    @Override
    public int hashCode() {
        return this.text.hashCode() ^ this.pos ^ this.begin ^ this.end;
    }
    
    @Deprecated
    @Override
    public Object clone() {
        try {
            final StringCharacterIterator other = (StringCharacterIterator)super.clone();
            return other;
        }
        catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException(e);
        }
    }
}
