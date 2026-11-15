package com.example.antigravity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.webkit.*
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    companion object {
        private const val PREFS_NAME = "antigravity_prefs"
        private const val KEY_SESSION_URL = "session_target_url"
        private const val DEFAULT_TEMPLATE_URL = "https://antigravity.google.com"
        private const val CHROME_UA =
            "Mozilla/5.0 (Linux; Android 14; 25102RKBEC) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.6723.107 Mobile Safari/537.36"
    }

    private lateinit var rootContainer: FrameLayout
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 100% immersive fullscreen: zero status bar, zero nav bar, zero borders
        @Suppress("DEPRECATION")
        run {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor("#0F172A")
            window.navigationBarColor = Color.parseColor("#0F172A")
        }

        rootContainer = FrameLayout(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.parseColor("#0B0F19"))
            isFocusable = true
            isFocusableInTouchMode = true
        }

        webView = WebView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.parseColor("#0B0F19"))
            isVerticalScrollBarEnabled = false
            isHorizontalScrollBarEnabled = false
            // Hardware acceleration
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
        }

        progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(3)
            )
            max = 100
            @Suppress("DEPRECATION")
            progressDrawable?.setColorFilter(
                Color.parseColor("#3B82F6"),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            visibility = View.VISIBLE
        }

        // Long press on progress bar allows re-opening session URL config dialog
        progressBar.setOnLongClickListener {
            showSessionConfigDialog(isFirstLaunch = false)
            true
        }

        rootContainer.addView(webView)
        rootContainer.addView(progressBar)
        setContentView(rootContainer)

        // Immersive fullscreen activation
        window.decorView.post {
            applyImmersiveFullScreen()
        }

        setupWebView()

        // Dynamic session URL loading: load configured session URL or prompt user with sleek dark dialog
        val savedUrl = getSavedSessionUrl()
        if (!savedUrl.isNullOrBlank()) {
            webView.loadUrl(savedUrl)
        } else {
            showSessionConfigDialog(isFirstLaunch = true)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.post {
                applyImmersiveFullScreen()
            }
        }
    }

    private fun applyImmersiveFullScreen() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.setDecorFitsSystemWindows(false)
                window.insetsController?.let { controller ->
                    controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                    controller.systemBarsBehavior =
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            } else {
                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                    )
            }
        } catch (_: Exception) {}
    }

    private fun getSavedSessionUrl(): String? {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_SESSION_URL, null)?.takeIf { it.isNotBlank() }
    }

    private fun saveSessionUrl(url: String) {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_SESSION_URL, url.trim()).apply()
    }

    private fun showSessionConfigDialog(isFirstLaunch: Boolean) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(!isFirstLaunch)

        val context = this
        val density = resources.displayMetrics.density

        // Root Dialog Card Container (Sleek Dark OLED Theme)
        val card = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(24), dp(24), dp(24), dp(24))
            val bg = GradientDrawable().apply {
                setColor(Color.parseColor("#1E293B")) // Slate 800
                cornerRadius = 16 * density
                setStroke((1 * density).toInt(), Color.parseColor("#334155")) // Slate 700 border
            }
            background = bg
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        // Title
        val titleView = TextView(context).apply {
            text = "Antigravity Session"
            textSize = 20f
            setTypeface(typeface, Typeface.BOLD)
            setTextColor(Color.parseColor("#F8FAFC"))
        }
        card.addView(titleView)

        // Subtitle / Instruction
        val subtitleView = TextView(context).apply {
            text = "Paste your Antigravity remote agent session URL below, or start with the default portal template."
            textSize = 13f
            setTextColor(Color.parseColor("#94A3B8"))
            setPadding(0, dp(8), 0, dp(16))
            setLineSpacing(0f, 1.2f)
        }
        card.addView(subtitleView)

        // URL Input Field
        val inputEdit = EditText(context).apply {
            hint = "https://antigravity.google.com/..."
            setHintTextColor(Color.parseColor("#64748B"))
            setTextColor(Color.parseColor("#F8FAFC"))
            textSize = 14f
            maxLines = 3
            isSingleLine = false
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_URI
            setPadding(dp(14), dp(12), dp(14), dp(12))

            val inputBg = GradientDrawable().apply {
                setColor(Color.parseColor("#0F172A")) // Slate 900
                cornerRadius = 8 * density
                setStroke((1 * density).toInt(), Color.parseColor("#334155"))
            }
            background = inputBg

            val existing = getSavedSessionUrl() ?: ""
            setText(existing)
            if (existing.isNotEmpty()) {
                setSelection(existing.length)
            }
        }
        card.addView(inputEdit)

        // Helper button: "Use Default Template"
        val templateBtn = TextView(context).apply {
            text = "Use Default Template: antigravity.google.com"
            textSize = 12f
            setTextColor(Color.parseColor("#60A5FA"))
            setPadding(0, dp(10), 0, dp(18))
            isClickable = true
            isFocusable = true
            setOnClickListener {
                inputEdit.setText(DEFAULT_TEMPLATE_URL)
                inputEdit.setSelection(DEFAULT_TEMPLATE_URL.length)
            }
        }
        card.addView(templateBtn)

        // Action Buttons Row
        val buttonRow = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.END
        }

        if (!isFirstLaunch) {
            val cancelBtn = Button(context).apply {
                text = "Cancel"
                setTextColor(Color.parseColor("#94A3B8"))
                setBackgroundColor(Color.TRANSPARENT)
                textSize = 14f
                setOnClickListener {
                    dialog.dismiss()
                    applyImmersiveFullScreen()
                }
            }
            buttonRow.addView(cancelBtn)
        }

        val connectBtn = Button(context).apply {
            text = "Connect & Launch"
            setTextColor(Color.WHITE)
            textSize = 14f
            val btnBg = GradientDrawable().apply {
                setColor(Color.parseColor("#2563EB")) // Primary Blue
                cornerRadius = 8 * density
            }
            background = btnBg
            setPadding(dp(16), dp(10), dp(16), dp(10))
            setOnClickListener {
                var url = inputEdit.text.toString().trim()
                if (url.isEmpty()) {
                    url = DEFAULT_TEMPLATE_URL
                } else if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://$url"
                }

                saveSessionUrl(url)
                webView.loadUrl(url)
                dialog.dismiss()
                applyImmersiveFullScreen()
            }
        }
        buttonRow.addView(connectBtn)
        card.addView(buttonRow)

        dialog.setContentView(card)

        dialog.setOnDismissListener {
            applyImmersiveFullScreen()
        }

        if (isFirstLaunch) {
            dialog.setOnCancelListener {
                finish()
            }
        }

        dialog.show()

        dialog.window?.let { win ->
            val displayMetrics = resources.displayMetrics
            win.setLayout(
                (displayMetrics.widthPixels * 0.90).toInt(),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.setSupportZoom(false)
        settings.builtInZoomControls = false
        settings.displayZoomControls = false
        settings.allowFileAccess = false
        settings.setSupportMultipleWindows(false)
        settings.javaScriptCanOpenWindowsAutomatically = true

        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.userAgentString = CHROME_UA

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(webView, true)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE

                CookieManager.getInstance().flush()

                // Inject AGY chat panel auto-expansion script
                view?.evaluateJavascript(
                    """
                    (function() {
                        // 1. Hide scrollbars
                        var style = document.createElement('style');
                        style.textContent = '::-webkit-scrollbar { display: none !important; }';
                        document.head.appendChild(style);

                        // 2. Auto-expand AGY panel if on desktop download welcome screen
                        setTimeout(function() {
                            var menuBtns = document.querySelectorAll('button, div[role="button"], a');
                            for (var i = 0; i < menuBtns.length; i++) {
                                var btn = menuBtns[i];
                                var aria = (btn.getAttribute('aria-label') || '').toLowerCase();
                                var text = (btn.innerText || '').toLowerCase();
                                if (aria.indexOf('menu') !== -1 || aria.indexOf('sidebar') !== -1 || aria.indexOf('agent') !== -1 || text.indexOf('agy') !== -1) {
                                    btn.click();
                                    break;
                                }
                            }
                        }, 1200);
                    })();
                    """.trimIndent(),
                    null
                )
            }

            override fun onRenderProcessGone(view: WebView?, detail: RenderProcessGoneDetail?): Boolean {
                try {
                    val urlToReload = getSavedSessionUrl() ?: DEFAULT_TEMPLATE_URL
                    view?.loadUrl(urlToReload)
                } catch (_: Exception) {}
                return true
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                if (request?.isForMainFrame == true) {
                    progressBar.visibility = View.GONE
                    // Prompt user with config dialog if the remote URL could not be resolved or reached
                    showSessionConfigDialog(isFirstLaunch = false)
                }
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url?.toString() ?: return false
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    return false
                }
                return false
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress < 100) {
                    progressBar.visibility = View.VISIBLE
                    progressBar.progress = newProgress
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        CookieManager.getInstance().flush()
    }

    override fun onStop() {
        super.onStop()
        CookieManager.getInstance().flush()
    }

    override fun onDestroy() {
        CookieManager.getInstance().flush()
        super.onDestroy()
    }

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showSessionConfigDialog(isFirstLaunch = false)
            return true
        }
        return super.onKeyLongPress(keyCode, event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            event?.startTracking()
            if (webView.canGoBack()) {
                webView.goBack()
                return true
            } else {
                // Return to desktop while keeping process resident in memory for 0ms instant resume
                moveTaskToBack(true)
                return true
            }
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            showSessionConfigDialog(isFirstLaunch = false)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }
}
