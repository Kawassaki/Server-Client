/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketfinalservidor;
import java.util.List;
import java.util.Map;

public class QueryBrowserObjectReturn {
	
	private List<Map<String, Object>> listResult;
	private List<ColumnQueryBrowser> columns;
	private boolean retornoSucesso;
	private String messageRetorno;
	
	public List<Map<String, Object>> getListResult() {
		return listResult;
	}
	public void setListResult(List<Map<String, Object>> listResult) {
		this.listResult = listResult;
	}
	public List<ColumnQueryBrowser> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnQueryBrowser> columns) {
		this.columns = columns;
	}
	
	public String getMessageRetorno(){
		return this.messageRetorno;
	}
	
	public void setMessageRetorno(String messageRetorno){
		this.messageRetorno = messageRetorno;
	}
	
	public boolean isRetornoSucesso() {
		return retornoSucesso;
	}
	public void setRetornoSucesso(boolean retornoSucesso) {
		this.retornoSucesso = retornoSucesso;
	}	
}