package org.sp.tproject.main.view;

import java.awt.Component;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class TodoListRenderer extends JCheckBox implements ListCellRenderer<TodoItem> {

    @Override
    public Component getListCellRendererComponent(JList<? extends TodoItem> list, TodoItem item, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        setEnabled(list.isEnabled());
        setSelected(item.isComplete());
        setFont(list.getFont());
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        setText(item.getTask());
        if(item != null){
            if(item.isComplete()){
                Map<TextAttribute, Object> attributes = new HashMap<>();
                attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
                setFont(getFont().deriveFont(attributes));
            } else {
                setFont(getFont().deriveFont(Font.PLAIN));
            }
        } 

        return this;
    }
}