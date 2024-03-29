package Commands;

import Entities.Database;
import Entities.Metadata;
import Entities.Result;
import Entities.ResultSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Select extends Command {

    private ArrayList<String> columns = new ArrayList<String>();
    private ArrayList<String> values = new ArrayList<String>();
    private String from;
    private String resultado;
    private boolean containsAsterisk;

    //criei uma lógica para não adicionar a mesma coluna duas vezes, pq no resultado ele 
    //chama novamente o método enterColumn_name no listener e adiciona denovo a mesma 
    public void addColumn(String column) {
        int aux = columns.size();
        if (columns.size() == 0) {
            this.columns.add(column);
        } else if (this.columns.get(this.columns.size() - 1).equals(column)) {

        }
    }

    public void addValue(String value) {
        this.values.add(value);
    }

    public ArrayList<String> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<String> columns) {
        this.columns = columns;
    }

    public String getFrom() {
        return from;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public boolean isContainsAsterisk() {
        return containsAsterisk;
    }

    public void setContainsAsterisk(boolean containsAsterisk) {
        this.containsAsterisk = containsAsterisk;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String where) {
        this.resultado = where;
    }

    @Override
    public void run(Database database) {
        Metadata metadata = database.findMetadata(this.from);
        File table = database.findTable(this.from);

        try {
            ResultSet resultSet = new ResultSet();
            resultSet.setNumColumns(metadata.getColumns().size());
            RandomAccessFile raf = new RandomAccessFile(table, "r");

            if (this.containsAsterisk) {
                Long tableLengthLong = raf.length();
                int tableLength = Integer.valueOf(tableLengthLong.toString());

                int recordCount = tableLength / metadata.getRecordSize();

                System.out.println(metadata.toString());

                for (int i = 0; i < recordCount; i++) {
                    // move o pointeiro no arquivo, de registro em registro
                    raf.seek(i * metadata.getRecordSize());

                    //itera de coluna em coluna
                    for (int j = 0; j < metadata.getColumns().size(); j++) {
                        // atributos das colunas
                        String column = metadata.getColumns().get(j);
                        String type = metadata.getTypes().get(j);
                        int byteSize = metadata.getByteSize()[j];

                        byte[] bytesValue = new byte[byteSize];

                        raf.read(bytesValue);

                        String value = "";

                        if (type.contains("char")) {
                            value = new String(bytesValue, 0, byteSize);
                            while (value.contains("  ")) {
                                value = value.replaceAll("  ", "");
                            }
                        } else if (type.contains("int")) {
                            value = ByteBuffer.wrap(bytesValue).getInt() + "";
                        } else if (type.contains("float")) {
                            value = ByteBuffer.wrap(bytesValue).getFloat() + "";
                        }
                        resultSet.addResult(new Result(column, value));
                    }
                }
                raf.close();
            }
            System.out.println("RESULTADO: " + resultSet.toString());
            resultado = resultSet.toString();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Insert.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Insert.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String toString() {
        if (columns.size() == 0) {
            return "Select" + "\n" + resultado;
        } else {
            return "Select{" + getColumns().toString() + " from=" + from + ", where=" + resultado + '}';
        }
    }
}
