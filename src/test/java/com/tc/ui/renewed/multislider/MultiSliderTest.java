package com.tc.ui.renewed.multislider;

import com.tc.ui.renewed.ui.BaseContainer;
import com.tc.ui.renewed.ui.BaseTCMain;
import com.tc.ui.renewed.ui.LabelValue;
import com.tc.ui.renewed.ui.ProportionalMultiSlider;
import com.tc.ui.renewed.ui.SliderValue;
import com.tc.ui.renewed.ui.builder.ProportionalMultiSliderBuilder;
import com.tc.utils.magic.util.Presenter;
import com.tc.utils.magic.util.ReadWriteAccessor;

import totalcross.sys.InvalidNumberException;
import totalcross.ui.Button;
import totalcross.ui.Check;
import totalcross.ui.Control;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;
import totalcross.ui.Slider;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.PressListener;
import totalcross.util.BigDecimal;

public class MultiSliderTest extends BaseContainer {
	Edit valorSlider;
	Edit qtSlider;
	ScrollContainer multiSliderContainer = new ScrollContainer(false, true);
	
	public MultiSliderTest(BaseTCMain<?> baseTest) {
		super(baseTest);
		valorSlider = new Edit("1234567890");
		qtSlider = new Edit("1234567890");
	}
	
	BigDecimal getValue() {
		if (valorSlider.getText().length() != 0) {
			try {
				return new BigDecimal(valorSlider.getText());
			} catch (InvalidNumberException e) {
				e.printStackTrace();
			}
		}
		return BigDecimal.ONE;
	}
	
	int getQt() {
		if (qtSlider.getText().length() != 0) {
			return Integer.valueOf(qtSlider.getText());
		}
		return 2;
	}

	@Override
	public void initUI() {
		super.initUI();
		Label lbSliderTotal = new Label("Valor soma slider: ");
		valorSlider.setText("100");
		
		Label lbQtSlider = new Label("Valor Qt slider: ");
		qtSlider.setText("4");
		
		add(lbSliderTotal, LEFT, AFTER, PREFERRED, PREFERRED);
		add(valorSlider, AFTER, SAME, FILL, SAME, lbSliderTotal);
		
		add(lbQtSlider, LEFT, AFTER, PREFERRED, PREFERRED);
		add(qtSlider, AFTER, SAME, FILL, SAME, lbQtSlider);
		
		Button btn = new Button("Create Multi Slider");
		add(btn, LEFT, AFTER, FILL, PREFERRED);
		add(multiSliderContainer, LEFT, AFTER, FILL, FILL);
		
		btn.addPressListener(new PressListener() {
			
			@Override
			public void controlPressed(ControlEvent arg0) {
				multiSliderContainer.removeAll();
				
				ProportionalMultiSlider<BigDecimalWrapper> multiSlider = createMultiSlider();
				boolean firstTime = true;
				int sizeEdt = fm.stringWidth("XXX.XXX");
				Control baseControl = null;
				for (SliderValue<BigDecimalWrapper> s: multiSlider.getSliders()) {
					Slider tcs = s.getSlider();
					Edit tcedt = s.getEdtValue();
					Check tccheck = s.getChkUnlocked();
					LabelValue<BigDecimalWrapper> tclb = s.getLb();
					if (firstTime) {
						firstTime = false;
						multiSliderContainer.add(tccheck, LEFT, TOP, PREFERRED, PREFERRED);
						multiSliderContainer.add(tcedt, AFTER, TOP, sizeEdt, fmH * 2);
						multiSliderContainer.add(tcs, AFTER, TOP, FILL, PREFERRED);
						multiSliderContainer.add(tclb, SAME, AFTER, FILL, fmH * 2- tcs.getHeight(), tcs);
					} else {
						multiSliderContainer.add(tccheck, LEFT, AFTER, PREFERRED, PREFERRED, baseControl);
						multiSliderContainer.add(tcedt, AFTER, SAME, sizeEdt, fmH * 2, tccheck);
						multiSliderContainer.add(tcs, AFTER, SAME, FILL, PREFERRED, tcedt);
						multiSliderContainer.add(tclb, SAME, AFTER, FILL, fmH * 2- tcs.getHeight(), tcs);
					}
					baseControl = tcedt;
				}
			}
		});
	}
	
	ProportionalMultiSlider<BigDecimalWrapper> createMultiSlider() {
		ProportionalMultiSliderBuilder<BigDecimalWrapper> builder = new ProportionalMultiSliderBuilder<>(new Presenter<BigDecimalWrapper>() {

			@Override
			public String stringfy(BigDecimalWrapper value) {
				return "" + value.getId();
			}
		}, new ReadWriteAccessor<BigDecimalWrapper, BigDecimal>() {

			@Override
			public BigDecimal getAttr(BigDecimalWrapper source) {
				return source.getValue();
			}

			@Override
			public void setAttr(BigDecimalWrapper source, BigDecimal value) {
				source.setValue(value);
			}
		});
		for (int i = getQt() - 1; i >= 0; i--) {
			BigDecimalWrapper wrapper = new BigDecimalWrapper();
			wrapper.setId(i);
			builder.addValue(wrapper);
		}
		builder.setSum(getValue());
		
		return builder.build();
	}
}
