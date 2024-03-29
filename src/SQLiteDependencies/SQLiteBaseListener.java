package SQLiteDependencies;

// Generated from SQLite.g4 by ANTLR 4.7.2
import Commands.Command;
import Commands.CreateTable;
import Commands.Insert;
import Commands.Select;
import Entities.Database;
import View.Tela;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SQLiteBaseListener implements SQLiteListener {

    private String tableName;
    private Command currentCommand;
    private Database database;
    ArrayList<String> ret;

    public Command getCurrentCommand() {
        return this.currentCommand;
    }

    public String getTableName() {
        return tableName;
    }

    public Database getDatabase() {
        return this.database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public boolean validarXML(String nomeArquivo) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema;
        try {
            schema = schemaFactory.newSchema(new File("udescdb.xsd"));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(nomeArquivo + ".xml")));
            JOptionPane.showMessageDialog(null, "Arquivo Validado");
            return true;
        } catch (SAXException ex) {
            JOptionPane.showMessageDialog(null, "Arquivo não Válido");
            return false;
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Arquivo não encontrado");
            return false;
        } catch (IOException ex) {
            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void insertXML(String nomeArquivo) throws ParserConfigurationException, SAXException, IOException {
        ret = new ArrayList<String>();
        this.currentCommand = new Insert();
        Insert command = (Insert) this.currentCommand;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);

        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(new File(nomeArquivo + ".xml"));
        Element root = doc.getDocumentElement();
        System.out.println(root.getNodeName());

        iteraXML(root.getChildNodes());

        NamedNodeMap atrib = doc.getElementsByTagName("table").item(0).getAttributes();
        command.setTableName(atrib.getNamedItem("name").getNodeValue());
        for (int i = 0; i < ret.size(); i++) {
            if (i % 2 == 0) {
                command.addColumn(ret.get(i));
            } else {
                command.addValue(ret.get(i));
            }
        }
        //command.addColumn(doc.getElementsByTagName("name").item(0).getTextContent());
        //command.addValue(doc.getElementsByTagName("value").item(0).getTextContent());
        //print apenas para ver se está carregando
        System.out.println("método no listener" + command.toString());
        ret = null;
        System.out.println(this.database.toString());

        command.run(this.database);
    }

    private ArrayList<String> iteraXML(NodeList filhos) {

        for (int i = 0; i < filhos.getLength(); i++) {
            Node filho = filhos.item(i);
            if (filho.getChildNodes().getLength() > 0) {
                iteraXML(filho.getChildNodes());
            } else {
                if (filho.getNodeValue().trim().length() > 0) {
                    ret.add(filho.getNodeValue());
                }
            }
        }
        return ret;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterParse(SQLiteParser.ParseContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitParse(SQLiteParser.ParseContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterError(SQLiteParser.ErrorContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitError(SQLiteParser.ErrorContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterSql_stmt_list(SQLiteParser.Sql_stmt_listContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitSql_stmt_list(SQLiteParser.Sql_stmt_listContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterSql_stmt(SQLiteParser.Sql_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitSql_stmt(SQLiteParser.Sql_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterAlter_table_stmt(SQLiteParser.Alter_table_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitAlter_table_stmt(SQLiteParser.Alter_table_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterAnalyze_stmt(SQLiteParser.Analyze_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitAnalyze_stmt(SQLiteParser.Analyze_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterAttach_stmt(SQLiteParser.Attach_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitAttach_stmt(SQLiteParser.Attach_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterBegin_stmt(SQLiteParser.Begin_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitBegin_stmt(SQLiteParser.Begin_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterCommit_stmt(SQLiteParser.Commit_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitCommit_stmt(SQLiteParser.Commit_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterCompound_select_stmt(SQLiteParser.Compound_select_stmtContext ctx) {
        System.out.println("enterCompound_select_stmt: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitCompound_select_stmt(SQLiteParser.Compound_select_stmtContext ctx) {
        System.out.println("exitCompound_select_stmt" + ctx.getText());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterCreate_index_stmt(SQLiteParser.Create_index_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitCreate_index_stmt(SQLiteParser.Create_index_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterCreate_table_stmt(SQLiteParser.Create_table_stmtContext ctx) {
        this.currentCommand = new CreateTable();
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitCreate_table_stmt(SQLiteParser.Create_table_stmtContext ctx) {
        CreateTable command = (CreateTable) this.currentCommand;
        try {
            command.run(this.database);
        } catch (Exception ex) {
            Logger.getLogger(SQLiteBaseListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterCreate_trigger_stmt(SQLiteParser.Create_trigger_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitCreate_trigger_stmt(SQLiteParser.Create_trigger_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterCreate_view_stmt(SQLiteParser.Create_view_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitCreate_view_stmt(SQLiteParser.Create_view_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterCreate_virtual_table_stmt(SQLiteParser.Create_virtual_table_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitCreate_virtual_table_stmt(SQLiteParser.Create_virtual_table_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterDelete_stmt(SQLiteParser.Delete_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitDelete_stmt(SQLiteParser.Delete_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterDelete_stmt_limited(SQLiteParser.Delete_stmt_limitedContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitDelete_stmt_limited(SQLiteParser.Delete_stmt_limitedContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterDetach_stmt(SQLiteParser.Detach_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitDetach_stmt(SQLiteParser.Detach_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterDrop_index_stmt(SQLiteParser.Drop_index_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitDrop_index_stmt(SQLiteParser.Drop_index_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterDrop_table_stmt(SQLiteParser.Drop_table_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitDrop_table_stmt(SQLiteParser.Drop_table_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterDrop_trigger_stmt(SQLiteParser.Drop_trigger_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitDrop_trigger_stmt(SQLiteParser.Drop_trigger_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterDrop_view_stmt(SQLiteParser.Drop_view_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitDrop_view_stmt(SQLiteParser.Drop_view_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterFactored_select_stmt(SQLiteParser.Factored_select_stmtContext ctx) {
        this.currentCommand = new Select();
        System.out.println("enterFactored_select_stmt: " + ctx.getText());

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitFactored_select_stmt(SQLiteParser.Factored_select_stmtContext ctx) {
        Select command = (Select) this.currentCommand;
        command.setContainsAsterisk(ctx.getText().contains("*"));
        command.run(this.database);

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterInsert_stmt(SQLiteParser.Insert_stmtContext ctx) {
        this.currentCommand = new Insert();
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitInsert_stmt(SQLiteParser.Insert_stmtContext ctx) {
        Insert command = (Insert) this.currentCommand;
        command.run(this.database);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterPragma_stmt(SQLiteParser.Pragma_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitPragma_stmt(SQLiteParser.Pragma_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterReindex_stmt(SQLiteParser.Reindex_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitReindex_stmt(SQLiteParser.Reindex_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterRelease_stmt(SQLiteParser.Release_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitRelease_stmt(SQLiteParser.Release_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterRollback_stmt(SQLiteParser.Rollback_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitRollback_stmt(SQLiteParser.Rollback_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterSavepoint_stmt(SQLiteParser.Savepoint_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitSavepoint_stmt(SQLiteParser.Savepoint_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterSimple_select_stmt(SQLiteParser.Simple_select_stmtContext ctx) {
        System.out.println("enterSimple_select_stmt: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitSimple_select_stmt(SQLiteParser.Simple_select_stmtContext ctx) {
        System.out.println("exitSimple_select_stmt: " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterSelect_stmt(SQLiteParser.Select_stmtContext ctx) {
        System.out.println("enterSelect_stmt: " + ctx.getText());
        this.currentCommand = new Select();
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitSelect_stmt(SQLiteParser.Select_stmtContext ctx) {
        System.out.println("exitSelect_stmt: " + ctx.getText());
        Select command = (Select) this.currentCommand;
        command.run(this.database);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterSelect_or_values(SQLiteParser.Select_or_valuesContext ctx) {
        System.out.println("enterSelect_or_values : " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitSelect_or_values(SQLiteParser.Select_or_valuesContext ctx) {
        System.out.println("exitSelect_or_values: " + ctx.getText());

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterUpdate_stmt(SQLiteParser.Update_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitUpdate_stmt(SQLiteParser.Update_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterUpdate_stmt_limited(SQLiteParser.Update_stmt_limitedContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitUpdate_stmt_limited(SQLiteParser.Update_stmt_limitedContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterVacuum_stmt(SQLiteParser.Vacuum_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitVacuum_stmt(SQLiteParser.Vacuum_stmtContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterColumn_def(SQLiteParser.Column_defContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitColumn_def(SQLiteParser.Column_defContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterType_name(SQLiteParser.Type_nameContext ctx) {
        CreateTable command = (CreateTable) this.currentCommand;
        command.addType(ctx.getText());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitType_name(SQLiteParser.Type_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterColumn_constraint(SQLiteParser.Column_constraintContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitColumn_constraint(SQLiteParser.Column_constraintContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterConflict_clause(SQLiteParser.Conflict_clauseContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitConflict_clause(SQLiteParser.Conflict_clauseContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterExpr(SQLiteParser.ExprContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitExpr(SQLiteParser.ExprContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterForeign_key_clause(SQLiteParser.Foreign_key_clauseContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitForeign_key_clause(SQLiteParser.Foreign_key_clauseContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterRaise_function(SQLiteParser.Raise_functionContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitRaise_function(SQLiteParser.Raise_functionContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterIndexed_column(SQLiteParser.Indexed_columnContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitIndexed_column(SQLiteParser.Indexed_columnContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterTable_constraint(SQLiteParser.Table_constraintContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitTable_constraint(SQLiteParser.Table_constraintContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterWith_clause(SQLiteParser.With_clauseContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitWith_clause(SQLiteParser.With_clauseContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterQualified_table_name(SQLiteParser.Qualified_table_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitQualified_table_name(SQLiteParser.Qualified_table_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterOrdering_term(SQLiteParser.Ordering_termContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitOrdering_term(SQLiteParser.Ordering_termContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterPragma_value(SQLiteParser.Pragma_valueContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitPragma_value(SQLiteParser.Pragma_valueContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterCommon_table_expression(SQLiteParser.Common_table_expressionContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitCommon_table_expression(SQLiteParser.Common_table_expressionContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterResult_column(SQLiteParser.Result_columnContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitResult_column(SQLiteParser.Result_columnContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterTable_or_subquery(SQLiteParser.Table_or_subqueryContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitTable_or_subquery(SQLiteParser.Table_or_subqueryContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterJoin_clause(SQLiteParser.Join_clauseContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitJoin_clause(SQLiteParser.Join_clauseContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterJoin_operator(SQLiteParser.Join_operatorContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitJoin_operator(SQLiteParser.Join_operatorContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterJoin_constraint(SQLiteParser.Join_constraintContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitJoin_constraint(SQLiteParser.Join_constraintContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterSelect_core(SQLiteParser.Select_coreContext ctx) {

        System.out.println("enterSelect_core" + ctx.getText());

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitSelect_core(SQLiteParser.Select_coreContext ctx) {

        System.out.println("exitSelect_core: " + ctx.getText());

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterCompound_operator(SQLiteParser.Compound_operatorContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitCompound_operator(SQLiteParser.Compound_operatorContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterCte_table_name(SQLiteParser.Cte_table_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitCte_table_name(SQLiteParser.Cte_table_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterSigned_number(SQLiteParser.Signed_numberContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitSigned_number(SQLiteParser.Signed_numberContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterLiteral_value(SQLiteParser.Literal_valueContext ctx) {
        if (this.currentCommand instanceof Insert) {
            Insert command = (Insert) this.currentCommand;
            command.addValue(ctx.getText());
        } else if (this.currentCommand instanceof Select) {
            Select command = (Select) this.currentCommand;
            command.addValue(ctx.getText());

        }
        //command.gravarEmBanco();

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitLiteral_value(SQLiteParser.Literal_valueContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterUnary_operator(SQLiteParser.Unary_operatorContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitUnary_operator(SQLiteParser.Unary_operatorContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterError_message(SQLiteParser.Error_messageContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitError_message(SQLiteParser.Error_messageContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterModule_argument(SQLiteParser.Module_argumentContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitModule_argument(SQLiteParser.Module_argumentContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterColumn_alias(SQLiteParser.Column_aliasContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitColumn_alias(SQLiteParser.Column_aliasContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterKeyword(SQLiteParser.KeywordContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitKeyword(SQLiteParser.KeywordContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterName(SQLiteParser.NameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitName(SQLiteParser.NameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterFunction_name(SQLiteParser.Function_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitFunction_name(SQLiteParser.Function_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterDatabase_name(SQLiteParser.Database_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitDatabase_name(SQLiteParser.Database_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterTable_name(SQLiteParser.Table_nameContext ctx) {

        if (this.currentCommand instanceof CreateTable) {
            CreateTable command = (CreateTable) this.currentCommand;
            command.setTableName(ctx.getText());

        } else if (this.currentCommand instanceof Insert) {
            Insert command = (Insert) this.currentCommand;
            command.setTableName(ctx.getText());

        } else if (this.currentCommand instanceof Select) {
            Select command = (Select) this.currentCommand;
            command.setFrom(ctx.getText());

        }

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitTable_name(SQLiteParser.Table_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterTable_or_index_name(SQLiteParser.Table_or_index_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitTable_or_index_name(SQLiteParser.Table_or_index_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterNew_table_name(SQLiteParser.New_table_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitNew_table_name(SQLiteParser.New_table_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterColumn_name(SQLiteParser.Column_nameContext ctx) {
        if (this.currentCommand instanceof CreateTable) {
            CreateTable command = (CreateTable) this.currentCommand;
            command.addColumn(ctx.getText());

        } else if (this.currentCommand instanceof Insert) {
            Insert command = (Insert) this.currentCommand;
            command.addColumn(ctx.getText());

        } else if (this.currentCommand instanceof Select) {
            System.out.println("adicionou coluna 1750" + ctx.getText());
            Select command = (Select) this.currentCommand;
            command.addColumn(ctx.getText());
        }

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitColumn_name(SQLiteParser.Column_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterCollation_name(SQLiteParser.Collation_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitCollation_name(SQLiteParser.Collation_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterForeign_table(SQLiteParser.Foreign_tableContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitForeign_table(SQLiteParser.Foreign_tableContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterIndex_name(SQLiteParser.Index_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitIndex_name(SQLiteParser.Index_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterTrigger_name(SQLiteParser.Trigger_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitTrigger_name(SQLiteParser.Trigger_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterView_name(SQLiteParser.View_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitView_name(SQLiteParser.View_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterModule_name(SQLiteParser.Module_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitModule_name(SQLiteParser.Module_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterPragma_name(SQLiteParser.Pragma_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitPragma_name(SQLiteParser.Pragma_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterSavepoint_name(SQLiteParser.Savepoint_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitSavepoint_name(SQLiteParser.Savepoint_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterTable_alias(SQLiteParser.Table_aliasContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitTable_alias(SQLiteParser.Table_aliasContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterTransaction_name(SQLiteParser.Transaction_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitTransaction_name(SQLiteParser.Transaction_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterAny_name(SQLiteParser.Any_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitAny_name(SQLiteParser.Any_nameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void visitTerminal(TerminalNode node) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.
     * </p>
     */
    @Override
    public void visitErrorNode(ErrorNode node) {
    }
}
