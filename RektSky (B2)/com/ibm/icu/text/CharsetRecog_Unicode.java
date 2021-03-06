package com.ibm.icu.text;

abstract class CharsetRecog_Unicode extends CharsetRecognizer
{
    @Override
    abstract String getName();
    
    @Override
    abstract CharsetMatch match(final CharsetDetector p0);
    
    static int codeUnit16FromBytes(final byte hi, final byte lo) {
        return (hi & 0xFF) << 8 | (lo & 0xFF);
    }
    
    static int adjustConfidence(final int codeUnit, int confidence) {
        if (codeUnit == 0) {
            confidence -= 10;
        }
        else if ((codeUnit >= 32 && codeUnit <= 255) || codeUnit == 10) {
            confidence += 10;
        }
        if (confidence < 0) {
            confidence = 0;
        }
        else if (confidence > 100) {
            confidence = 100;
        }
        return confidence;
    }
    
    static class CharsetRecog_UTF_16_BE extends CharsetRecog_Unicode
    {
        @Override
        String getName() {
            return "UTF-16BE";
        }
        
        @Override
        CharsetMatch match(final CharsetDetector det) {
            final byte[] input = det.fRawInput;
            int confidence = 10;
            final int bytesToCheck = Math.min(input.length, 30);
            for (int charIndex = 0; charIndex < bytesToCheck - 1; charIndex += 2) {
                final int codeUnit = CharsetRecog_Unicode.codeUnit16FromBytes(input[charIndex], input[charIndex + 1]);
                if (charIndex == 0 && codeUnit == 65279) {
                    confidence = 100;
                    break;
                }
                confidence = CharsetRecog_Unicode.adjustConfidence(codeUnit, confidence);
                if (confidence == 0) {
                    break;
                }
                if (confidence == 100) {
                    break;
                }
            }
            if (bytesToCheck < 4 && confidence < 100) {
                confidence = 0;
            }
            if (confidence > 0) {
                return new CharsetMatch(det, this, confidence);
            }
            return null;
        }
    }
    
    static class CharsetRecog_UTF_16_LE extends CharsetRecog_Unicode
    {
        @Override
        String getName() {
            return "UTF-16LE";
        }
        
        @Override
        CharsetMatch match(final CharsetDetector det) {
            final byte[] input = det.fRawInput;
            int confidence = 10;
            final int bytesToCheck = Math.min(input.length, 30);
            for (int charIndex = 0; charIndex < bytesToCheck - 1; charIndex += 2) {
                final int codeUnit = CharsetRecog_Unicode.codeUnit16FromBytes(input[charIndex + 1], input[charIndex]);
                if (charIndex == 0 && codeUnit == 65279) {
                    confidence = 100;
                    break;
                }
                confidence = CharsetRecog_Unicode.adjustConfidence(codeUnit, confidence);
                if (confidence == 0) {
                    break;
                }
                if (confidence == 100) {
                    break;
                }
            }
            if (bytesToCheck < 4 && confidence < 100) {
                confidence = 0;
            }
            if (confidence > 0) {
                return new CharsetMatch(det, this, confidence);
            }
            return null;
        }
    }
    
    abstract static class CharsetRecog_UTF_32 extends CharsetRecog_Unicode
    {
        abstract int getChar(final byte[] p0, final int p1);
        
        @Override
        abstract String getName();
        
        @Override
        CharsetMatch match(final CharsetDetector det) {
            final byte[] input = det.fRawInput;
            final int limit = det.fRawLength / 4 * 4;
            int numValid = 0;
            int numInvalid = 0;
            boolean hasBOM = false;
            int confidence = 0;
            if (limit == 0) {
                return null;
            }
            if (this.getChar(input, 0) == 65279) {
                hasBOM = true;
            }
            for (int i = 0; i < limit; i += 4) {
                final int ch = this.getChar(input, i);
                if (ch < 0 || ch >= 1114111 || (ch >= 55296 && ch <= 57343)) {
                    ++numInvalid;
                }
                else {
                    ++numValid;
                }
            }
            if (hasBOM && numInvalid == 0) {
                confidence = 100;
            }
            else if (hasBOM && numValid > numInvalid * 10) {
                confidence = 80;
            }
            else if (numValid > 3 && numInvalid == 0) {
                confidence = 100;
            }
            else if (numValid > 0 && numInvalid == 0) {
                confidence = 80;
            }
            else if (numValid > numInvalid * 10) {
                confidence = 25;
            }
            return (confidence == 0) ? null : new CharsetMatch(det, this, confidence);
        }
    }
    
    static class CharsetRecog_UTF_32_BE extends CharsetRecog_UTF_32
    {
        @Override
        int getChar(final byte[] input, final int index) {
            return (input[index + 0] & 0xFF) << 24 | (input[index + 1] & 0xFF) << 16 | (input[index + 2] & 0xFF) << 8 | (input[index + 3] & 0xFF);
        }
        
        @Override
        String getName() {
            return "UTF-32BE";
        }
    }
    
    static class CharsetRecog_UTF_32_LE extends CharsetRecog_UTF_32
    {
        @Override
        int getChar(final byte[] input, final int index) {
            return (input[index + 3] & 0xFF) << 24 | (input[index + 2] & 0xFF) << 16 | (input[index + 1] & 0xFF) << 8 | (input[index + 0] & 0xFF);
        }
        
        @Override
        String getName() {
            return "UTF-32LE";
        }
    }
}
