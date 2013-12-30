package com.xue.replay;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.os.Bundle;

import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xue.replay.handler.EventSourceHandle;

public class MainActivity extends Activity implements LayoutChangeListener, OnClickListener {

	protected ScrollLayout layout;
	protected ImageView imageView;
	protected Button tab1;
	protected Button tab2;

	private WindowManager windowManager = null;
	private WindowManager.LayoutParams windowManagerParams = null;
	private FloatView floatView = null;

	private ExpandableListView expandableListView;
	private ListView listView;

	private EventSourceHandle mEventSourceHandle;

	// private List<Event> mEvents = new ArrayList<Event>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		layout = (ScrollLayout) findViewById(R.id.scrolllayout);
		layout.addChangeListener(this);
		tab1 = (Button) findViewById(R.id.tab1);
		tab2 = (Button) findViewById(R.id.tab2);

		imageView = (ImageView) findViewById(R.id.top_bar_select);

		View mEventView = LayoutInflater.from(this).inflate(R.layout.event_layout, null);
		expandableListView = (ExpandableListView) mEventView.findViewById(R.id.expandableListView1);

		View mPlanView = LayoutInflater.from(this).inflate(R.layout.plan_layout, null);
		listView = (ListView) mPlanView.findViewById(R.id.listView1);

		registerForContextMenu(expandableListView);
		mEventSourceHandle = new EventSourceHandle(this, expandableListView);
		mEventSourceHandle.load();

		layout.addView(mEventView);

		layout.addView(mPlanView);

		layout.setToScreen(0);

	//	createView();

	}

	@Override
	public void doChange(int lastIndex, int currentIndex) {
		// TODO Auto-generated method stub
		if (lastIndex != currentIndex) {
			TranslateAnimation animation = null;
			LinearLayout layout = null;
			switch (currentIndex) {
			case 0:
				if (lastIndex == 1) {
					layout = (LinearLayout) tab2.getParent();
					animation = new TranslateAnimation(layout.getLeft(), 0, 0, 0);
				}
				break;
			case 1:
				if (lastIndex == 0) {
					layout = (LinearLayout) tab1.getParent();
					animation = new TranslateAnimation(layout.getLeft(), ((LinearLayout) tab2.getParent()).getWidth(),
							0, 0);
				}
				break;
			}
			animation.setDuration(300);
			animation.setFillAfter(true);
			imageView.startAnimation(animation);
		}
	}

	private void createView() {
		floatView = new FloatView(getApplicationContext());
		floatView.setOnClickListener(this);
		floatView.setImageResource(R.drawable.ic_launcher); // ����򵥵����Դ��icon������ʾ
		// ��ȡWindowManager
		windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		// ����LayoutParams(ȫ�ֱ�������ز���
		windowManagerParams = ((FloatApplication) getApplication()).getWindowParams();
		windowManagerParams.type = LayoutParams.TYPE_PHONE; // ����window type
		windowManagerParams.format = PixelFormat.RGBA_8888; // ����ͼƬ��ʽ��Ч��Ϊ����͸��
		// ����Window flag
		windowManagerParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE;
		/*
		 * ע�⣬flag��ֵ����Ϊ�� LayoutParams.FLAG_NOT_TOUCH_MODAL ��Ӱ�������¼�
		 * LayoutParams.FLAG_NOT_FOCUSABLE ���ɾ۽�
		 * LayoutParams.FLAG_NOT_TOUCHABLE ���ɴ���
		 */
		// ������������Ͻǣ����ڵ������
		windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
		// ����Ļ���Ͻ�Ϊԭ�㣬����x��y��ʼֵ
		windowManagerParams.x = 0;
		windowManagerParams.y = 0;
		// ������ڳ������
		windowManagerParams.width = LayoutParams.WRAP_CONTENT;
		windowManagerParams.height = LayoutParams.WRAP_CONTENT;
		// ��ʾmyFloatViewͼ��
		windowManager.addView(floatView, windowManagerParams);
	}

	public void onDestroy() {
		super.onDestroy();
		// �ڳ����˳�(Activity��٣�ʱ������
	//	windowManager.removeView(floatView);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == tab1) {
			layout.snapToScreen(0);
			return;
		}
		if (v == tab2) {
			layout.snapToScreen(1);
			return;
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) menuInfo;
		LinearLayout linearLayout = (LinearLayout) info.targetView;
		String text = ((TextView) (linearLayout.getChildAt(1))).getText().toString();
		int type = ExpandableListView.getPackedPositionType(info.packedPosition);

		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
			int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);
			Toast.makeText(this, text + ": Child " + childPos + " clicked in group " + groupPos, Toast.LENGTH_SHORT)
					.show();

		}
		if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
			int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);

			showSelectDialog(0, groupPos);
		}

	}

	private void setUpActionBar(boolean showTabs, int selTabs) {
		// TODO Auto-generated method stub
		android.app.ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		// CompatActionBarNavHandler handler = new
		// CompatActionBarNavHandler((CompatActionBarNavListener) this);
		// actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_LIST);
		// SpinnerAdapter adapter = new ArrayAdapter<String>(this,
		// R.layout.actionbar_list_item, CHOOSE);
		// actionBar.setListNavigationCallbacks(adapter, null);
		// actionBar.setDisplayUseLogoEnabled(true);
	}

	private void showSelectDialog(int type, final int position) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String groupName = expandableListView.getItemAtPosition(position).toString();
		builder.setTitle(groupName);
		switch (type) {
		case 0:
			builder.setItems(getResources().getStringArray(R.array.dialog0), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					switch (which) {
					case 0:
						if (expandableListView.getChildAt(position).findViewById(R.id.group_item) != null) {
							Toast.makeText(MainActivity.this, "this is a group", Toast.LENGTH_SHORT).show();
						} else {

						}
						break;
					case 1:
						if (mEventSourceHandle.delete(position)) {
							Toast.makeText(MainActivity.this, getString(R.string.success), Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(MainActivity.this, getString(R.string.fail), Toast.LENGTH_SHORT).show();
						}

						break;
					case 2:
						break;
					}
				}
			});
			builder.show();
			break;
		case 1:
			break;
		}

	}

	/*
	 * public boolean onContextItemSelected(MenuItem item) {
	 * 
	 * ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo)
	 * item.getMenuInfo(); LinearLayout linearLayout = (LinearLayout)
	 * info.targetView; String text = ((TextView)
	 * (linearLayout.getChildAt(1))).getText().toString(); int type =
	 * ExpandableListView.getPackedPositionType(info.packedPosition);
	 * 
	 * if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) { int groupPos
	 * = ExpandableListView.getPackedPositionGroup(info.packedPosition); int
	 * childPos =
	 * ExpandableListView.getPackedPositionChild(info.packedPosition);
	 * Toast.makeText(this, text + ": Child " + childPos + " clicked in group "
	 * + groupPos, Toast.LENGTH_SHORT) .show(); return true; } if (type ==
	 * ExpandableListView.PACKED_POSITION_TYPE_GROUP) { int groupPos =
	 * ExpandableListView.getPackedPositionGroup(info.packedPosition);
	 * Toast.makeText(this, text + ": Group " + groupPos + " clicked",
	 * Toast.LENGTH_SHORT).show(); return true; }
	 * 
	 * return false; }
	 */

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_add_context:
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			final EditText mEditText = new EditText(MainActivity.this);
			builder.setTitle(getString(R.string.dialog_title));
			builder.setView(mEditText);
			builder.setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String group = mEditText.getText().toString();
					if (!group.equals("")) {
						if (mEventSourceHandle.add(group)) {
							Toast.makeText(MainActivity.this, getString(R.string.success), Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(MainActivity.this, getString(R.string.fail), Toast.LENGTH_SHORT).show();
						}
					}
				}
			});
			builder.setNegativeButton(getString(R.string.dialog_cancel), null);

			builder.show();

			break;
		case R.id.action_settings:
			break;

		}
		return super.onMenuItemSelected(featureId, item);
	}
}
