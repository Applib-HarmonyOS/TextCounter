package com.github.premnirmal.textcounter;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.Component.BindStateChangedListener;
import ohos.agp.components.Text;
import ohos.app.Context;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import com.github.premnirmal.textcounter.formatters.CommaSeparatedDecimalFormatter;
import com.github.premnirmal.textcounter.formatters.DecimalFormatter;
import com.github.premnirmal.textcounter.formatters.IntegerFormatter;
import com.github.premnirmal.textcounter.formatters.NoFormatter;
import com.hmos.compat.utils.AttrUtils;

/**
 * Created by prem on 10/28/14.
 * </p>
 * A TextView that counts values depending on the attributes set via xml or via java.
 * Depending on the value set in {@link #setAutoStart(boolean)}, the counter will start.
 * You may call {@link #start()} to start manually at any time.
 */
public class CounterView extends Text implements BindStateChangedListener {
    private static final HiLogLabel HI_LOG_LABEL = new HiLogLabel(0, 0, "CounterView");

    /**
     * 5 milliseconds.
     */
    public static final long DEFAULT_INTERVAL = 5L;

    /**
     * increment by 10 units for each time interval.
     */
    public static final float DEFAULT_INCREMENT = 10f;
    protected String text;
    protected String prefix;
    protected String suffix;

    /**
     * Using floats because harmony's attributes do not support longs.
     */
    protected long timeInterval;
    protected float increment;
    protected float startValue;
    protected float endValue;
    protected CounterType counterType;
    protected boolean autoStart;
    protected boolean autoFormat;
    protected Counter counter;
    protected Formatter formatter;
    EventHandler mHandler;

    /**
     * CounterView Constructor.
     *
     *  @param context - context for CounterView constructor
     *
     */
    public CounterView(Context context) {
        super(context);
        mHandler = new EventHandler(EventRunner.getMainEventRunner());
        init(context, null, "0", 0);
        setBindStateChangedListener(this);
    }

    /**
     * CounterView Constructor.
     *
     *  @param context - context for CounterView constructor
     *  @param attrs - attributes
     *
     */
    public CounterView(Context context, AttrSet attrs) {
        super(context, attrs);
        mHandler = new EventHandler(EventRunner.getMainEventRunner());
        init(context, attrs, "0", 0);
        setBindStateChangedListener(this);
    }

    /**
     * CounterView Constructor.
     *
     *  @param context - context for CounterView constructor
     *  @param attrs - attributes
     *  @param defStyleAttr - defStyle attribute
     *
     */
    public CounterView(Context context, AttrSet attrs, String defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHandler = new EventHandler(EventRunner.getMainEventRunner());
        init(context, attrs, defStyleAttr, 0);
        setBindStateChangedListener(this);
    }

    /**
     * CounterView Constructor.
     *
     *  @param context - context for CounterView constructor
     *  @param attrs - attributes
     *  @param defStyleAttr - defStyle attribute
     * @param defStyleRes - defStyleRes attribute
     */
    public CounterView(Context context, AttrSet attrs, String defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        mHandler = new EventHandler(EventRunner.getMainEventRunner());
        init(context, attrs, defStyleAttr, defStyleRes);
        setBindStateChangedListener(this);
    }

    protected void init(Context context, AttrSet attrs, String defStyleAttr, int defStyleRes) {
        if (attrs == null) {
            this.prefix = "";
            this.suffix = "";
            this.text = "";
            this.timeInterval = DEFAULT_INTERVAL;
            this.increment = DEFAULT_INCREMENT;
            this.startValue = 0f;
            this.endValue = 0f;
            this.autoStart = false;
            this.autoFormat = true;
            this.counterType = CounterType.NUMBER;
            return;
        }
        try {
            final CharSequence prefix1 = AttrUtils.getStringFromAttr(attrs, "prefix");
            if (prefix1 != null) {
                this.prefix = prefix1.toString();
            } else {
                this.prefix = "";
            }
            final CharSequence suffix1 = AttrUtils.getStringFromAttr(attrs, "suffix");
            if (suffix1 != null) {
                this.suffix = suffix1.toString();
            } else {
                this.suffix = "";
            }
            final CharSequence text1 = AttrUtils.getCharSequenceFromAttr(attrs, "ohos_text");
            if (text1 != null) {
                this.text = text1.toString();
            } else {
                this.text = "";
            }
            this.timeInterval = (long) AttrUtils.getFloatFromAttr(attrs, "timeInterval", DEFAULT_INTERVAL);
            this.increment = AttrUtils.getFloatFromAttr(attrs, "incrementValue", DEFAULT_INCREMENT);
            this.startValue = AttrUtils.getFloatFromAttr(attrs, "startValue", 0f);
            this.endValue = AttrUtils.getFloatFromAttr(attrs, "endValue", 0f);
            this.autoStart = AttrUtils.getBooleanFromAttr(attrs, "autoStart", true);
            this.autoFormat = AttrUtils.getBooleanFromAttr(attrs, "formatText", true);
            final int type = getHandletype(AttrUtils.getStringFromAttr(attrs, "type", ""));
            switch (type) {
                case 0:
                    counterType = CounterType.NUMBER;
                    break;
                case 1:
                    counterType = CounterType.DECIMAL;
                    break;
                case 2:
                    counterType = CounterType.BOTH;
                    break;
                default:
                    HiLog.debug(HI_LOG_LABEL, "default switch case in init");
            }
        } finally {
            //finally block
        }
        counter = new Counter(this, startValue, endValue, timeInterval, increment, mHandler);
        updateCounterType();
    }

    protected void updateCounterType() {
        switch (counterType) {
            case NUMBER:
                formatter = new IntegerFormatter();
                break;
            case DECIMAL:
                formatter = new DecimalFormatter();
                break;
            case BOTH:
                formatter = new CommaSeparatedDecimalFormatter();
                break;
            default:
                HiLog.debug(HI_LOG_LABEL, "default switch case in updateCounterType");
        }
    }

    void setCurrentTextValue(final float number) {
        text = formatter.format(prefix, suffix, number);
        String param = CounterView.changeParamToString(text);
        setText(param);
    }

    @Override
    public void onComponentBoundToWindow(Component component) {
        if (autoStart) {
            start();
        }
    }

    @Override
    public void onComponentUnboundFromWindow(Component component) {
        //empty
    }

    /**
     * Start the counter. This method will be called if autostart is set to true
     */
    public void start() {
        mHandler.removeTask(counter);
        mHandler.postTask(counter);
    }

    /**
     * Set the prefix for the number.
     *
     * @param prefix prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Set the suffix for the number.
     *
     * @param suffix suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * Set the time interval between each increment. Default is {@link #DEFAULT_INTERVAL}
     *
     * @param timeInterval timeinterval
     */
    public void setTimeInterval(long timeInterval) {
        this.timeInterval = timeInterval;
        counter = new Counter(this, startValue, endValue, timeInterval, increment, mHandler);
    }

    /**
     * Set the increment between the two values. Can be negative or positive. Default is
     * {@link #DEFAULT_INCREMENT}
     *
     * @param increment increment
     */
    public void setIncrement(float increment) {
        this.increment = increment;
        counter = new Counter(this, startValue, endValue, timeInterval, increment, mHandler);
    }

    /**
     * Set the value the counter should start at.
     *
     * @param startValue startvalue
     */
    public void setStartValue(float startValue) {
        this.startValue = startValue;
        counter = new Counter(this, startValue, endValue, timeInterval, increment, mHandler);
    }

    /**
     * Set the value the counter should end at.
     *
     * @param endValue endvalue
     */
    public void setEndValue(float endValue) {
        this.endValue = endValue;
        counter = new Counter(this, startValue, endValue, timeInterval, increment, mHandler);
    }

    /**
     * Set the counterType. See {@link CounterType} for further details.
     *
     * @param counterType counterType
     */
    public void setCounterType(CounterType counterType) {
        this.counterType = counterType;
        updateCounterType();
    }

    public void setAutoStart(boolean autoStart) {
        this.autoStart = autoStart;
    }

    /**
     * Allow the view to auto format the number depending on the {@link CounterType}.
     * set using {@link #setCounterType(CounterType)}
     *
     * @param formatText formatText
     */
    public void setAutoFormat(boolean formatText) {
        if (autoFormat) {
            if (counterType == CounterType.NUMBER) {
                formatter = new IntegerFormatter();
            } else {
                formatter = new DecimalFormatter();
            }
        } else {
            formatter = new NoFormatter();
        }
        this.autoFormat = formatText;
    }

    /**
     * Set a custom {@link Formatter} to format the text before.
     * {@link #setText(String)} is called
     *
     * @param formatter formatter
     */
    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

    public static String changeParamToString(CharSequence charSequence) {
        return charSequence.toString();
    }

    /**
     * getHandletype.
     *
     * @param mode mode
     * @return int
     */
    public int getHandletype(String mode) {
        switch (mode) {
            case "integer":
                return 0;
            case "decimal":
                return 1;
            case "both":
                return 2;
            default:
                HiLog.debug(HI_LOG_LABEL, "default switch case in getHandletype");
        }
        return 0;
    }
}
