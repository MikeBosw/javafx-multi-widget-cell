javafx-multi-widget-cell
========================

JavaFX: multi-widget cell

A tiny extension of the JavaFX controls.

The editable cells that come with JavaFX 2.2 each support a single kind of editing widget. You can make a TextFieldTableCell or a ComboxBoxTableCell, for example, which require that the cell commit before knowing its own content either to the TextField or the ComboBox. Multi-widget cells are cells that can choose their editing widgets based on their current content.

So far there is the TableCell implementation of a multi-widget cell, and there is support for the ComboBox and TextField. But the other implementations (ListCell; ChoiceBox; etc.) would work the same way.

To build:

1. Install Java 7u7 or later
2. Install Maven 3
3. Update the parent pom's JAVA_HOME variable to your JDK7 installation

To use:

Use MultiWidgetTableCell in your TableView. The MultiWidgetTableCell constructor takes a CellWidgetDecider, where the meat is: the logic for dynamically choosing which widget a cell should use when edited.

From the sample module:

```java
//1. this helper will be used when we want a TextField widget in the cell
final TextFieldCellHelper<String> helperA = TextFieldCellHelper.create();

//2. this helper will be used when we want a ComboBox widget in the cell
final CellWidgetHelper<ComboBox<String>, String> helperB = ComboBoxCellHelper.create();

//3. create the decider to make the choice
final CellWidgetDecider<String> decider = new CellWidgetDecider<String>() {
    @Override
    public <C extends Cell<String> & CellWidgetDecidable<String>> void decide(C cell) {
        final String value = cell.getItem();
        if (value == null || value.isEmpty()) {
            cell.use(helperA);
            return;
        }
        if (value.toUpperCase().charAt(0) >= 'N') {
            cell.use(helperA);
        } else {
            cell.use(helperB);
        }
    }
};

//4. go
_barColumn.setCellFactory(new Callback<TableColumn<StringPair, String>, TableCell<StringPair, String>>() {
    @Override
    public TableCell<StringPair, String> call(TableColumn<StringPair, String> col) {
        return new MultiWidgetTableCell<>(decider);
    }
});
```
