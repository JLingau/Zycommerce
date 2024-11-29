package utils;

import javax.swing.*;
import javax.swing.text.*;

public class NumberInputField extends JTextField {
    public NumberInputField(int columns) {
        ((AbstractDocument) getDocument()).setDocumentFilter(new PositiveNumberFilter());
        super.setColumns(columns);
    }

    private class PositiveNumberFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (isValidInput(fb.getDocument(), offset, string)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (isValidInput(fb.getDocument(), offset, text)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }

        private boolean isValidInput(Document doc, int offset, String input) throws BadLocationException {
            String currentText = doc.getText(0, doc.getLength());
            String newText = currentText.substring(0, offset) + input + currentText.substring(offset);
            return newText.matches("^(0|([1-9]\\d*)(\\.\\d+)?)?$");
        }
    }
}
