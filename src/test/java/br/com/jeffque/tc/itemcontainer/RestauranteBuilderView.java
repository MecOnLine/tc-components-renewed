package br.com.jeffque.tc.itemcontainer;

import br.com.jeffque.tc.ui.ValueableContainer;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.FocusListener;

public class RestauranteBuilderView extends ValueableContainer<Restaurante> {
	private static final String intCharset = Edit.numbersSet;
	private static final String numericCharset = intCharset + ".";
	
	Label lblNome = new Label("Nome: ");
	Label lblSatisfacao = new Label("Satisfacao: ");
	Label lblTipo = new Label("Tipo: ");
	Label lblPreco = new Label("Preco: ");
	Label lblDist = new Label("Distancia: ");
	Label lblTempo = new Label("Tempo: ");
	
	Edit edtNome;
	Edit edtSatisfacao;
	Edit edtTipo;
	Edit edtPreco;
	Edit edtDist;
	Edit edtTempo;
	
	public RestauranteBuilderView() {
		super(new RestauranteBuilder());
		
		lblNome = new Label("Nome: ");
		lblSatisfacao = new Label("Satisfacao: ");
		lblTipo = new Label("Tipo: ");
		lblPreco = new Label("Preco: ");
		lblDist = new Label("Distancia: ");
		lblTempo = new Label("Tempo: ");
		
		edtNome = new Edit();
		edtSatisfacao = new Edit();
		edtTipo = new Edit();
		edtPreco = new Edit();
		edtDist = new Edit();
		edtTempo = new Edit();
		
		edtSatisfacao.setValidChars(numericCharset);
		edtPreco.setValidChars(intCharset);
		edtDist.setValidChars(numericCharset);
		edtTempo.setValidChars(intCharset);
		
		reset();
	}
	
	public void reset() {
		getBuilder().reset();
	}
	
	@Override
	public void initUI() {
		FocusListener focusListener = new FocusListener() {
			
			@Override
			public void focusOut(ControlEvent e) {
				Edit edt = (Edit) e.target;
				
				doAction(edt);
			}

			@Override
			public void focusIn(ControlEvent e) {
			}
			
			private void doAction(Edit edt) {
				String text = edt.getText();
				if (edt == edtNome) {
					getBuilder().setNome(text);
				} else if (edt == edtSatisfacao) {
					getBuilder().setSatisfacao(text);
				} else if (edt == edtTipo) {
					getBuilder().setTipo(text);
				} else if (edt == edtPreco) {
					getBuilder().setPreco(text);
				} else if (edt == edtDist) {
					getBuilder().setDist(text);
				} else if (edt == edtTempo) {
					getBuilder().setTempo(text);
				}
			}
		};
		
		edtNome.addFocusListener(focusListener);
		edtSatisfacao.addFocusListener(focusListener);
		edtTipo.addFocusListener(focusListener);
		edtPreco.addFocusListener(focusListener);
		edtDist.addFocusListener(focusListener);
		edtTempo.addFocusListener(focusListener);
		
		add(lblNome, LEFT, TOP, PREFERRED, PREFERRED);
		add(edtNome, AFTER, SAME, FILL, SAME);
		
		add(lblSatisfacao, LEFT, AFTER, PREFERRED, PREFERRED);
		add(edtSatisfacao, AFTER, SAME, FILL, SAME);
		
		add(lblTipo, LEFT, AFTER, PREFERRED, PREFERRED);
		add(edtTipo, AFTER, SAME, FILL, SAME);
		
		add(lblPreco, LEFT, AFTER, PREFERRED, PREFERRED);
		add(edtPreco, AFTER, SAME, FILL, SAME);
		
		add(lblDist, LEFT, AFTER, PREFERRED, PREFERRED);
		add(edtDist, AFTER, SAME, FILL, SAME);
		
		add(lblTempo, LEFT, AFTER, PREFERRED, PREFERRED);
		add(edtTempo, AFTER, SAME, FILL, SAME);
	}
	
	private RestauranteBuilder getBuilder() {
		return (RestauranteBuilder) super.getValue();
	}
	
	@Override
	public int getPreferredHeight() {
		return lblNome.getPreferredHeight() + lblSatisfacao.getPreferredHeight() +
				lblTipo.getPreferredHeight() + lblPreco.getPreferredHeight() +
				lblDist.getPreferredHeight() + lblTempo.getPreferredHeight();
	}
	
	@Override
	public Restaurante getValue() {
		return getBuilder().build();
	}

}
