# -*- coding: UTF-8 -*-


import sys
import io

from gevent.libev.corecext import os

sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')
import jieba
from sklearn.externals import joblib
import matplotlib.pyplot as plt
from sklearn.naive_bayes import MultinomialNB

"""
函数说明:中文文本处理
Parameters:
    folder_path - 文本存放的路径
    test_size - 测试集占比，默认占所有数据集的百分之20
Returns:
    all_words_list - 按词频降序排序的训练集列表
    train_data_list - 训练集列表
    test_data_list - 测试集列表
    train_class_list - 训练集标签列表
    test_class_list - 测试集标签列表
"""


def TextProcessing(test_str_list, test_class_list):
    data_list = []  # 数据集数据
    class_list = []  # 数据集类别
    # test_class_list = []

    for i in range(0, len(test_str_list), 1):
        test_str = str(test_str_list[i])
        word_cut = jieba.cut(test_str, cut_all=False)
        word_list = list(word_cut)

        data_list.append(word_list)  # 添加数据集数据
        class_list = test_class_list  # 添加数据集类别

    data_class_list = list(zip(data_list, class_list))

    test_list = data_class_list  # 测试集
    test_data_list, test_class_list = zip(*test_list)  # 测试集解压缩
    return test_data_list, test_class_list


def words_dict(all_words_list, deleteN, stopwords_set=set()):
    feature_words = []  # 特征列表
    n = 1
    for t in range(deleteN, len(all_words_list), 1):
        if n > 1000:  # feature_words的维度为1000
            break
            # 如果这个词不是数字，并且不是指定的结束语，并且单词长度大于1小于5，那么这个词就可以作为特征词
        if not all_words_list[t].isdigit() and all_words_list[t] not in stopwords_set and 1 < len(
                all_words_list[t]) < 5:
            feature_words.append(all_words_list[t])
        n += 1
    return feature_words


def MakeWordsSet(words_file):
    words_set = set()  # 创建set集合
    with open(words_file, 'r', encoding='utf-8') as f:  # 打开文件
        for line in f.readlines():  # 一行一行读取
            word = line.strip()  # 去回车
            if len(word) > 0:  # 有文本，则添加到words_set中
                words_set.add(word)
    return words_set  # 返回处理结果


"""
函数说明:根据feature_words将文本向量化
Parameters:
    train_data_list - 训练集
    test_data_list - 测试集
    feature_words - 特征集
Returns:
    train_feature_list - 训练集向量化列表
    test_feature_list - 测试集向量化列表
"""


def TextFeatures(test_data_list, feature_words):
    def text_features(text, feature_words):  # 出现在特征集中，则置1
        text_words = set(text)
        features = [1 if word in text_words else 0 for word in feature_words]
        return features

    test_feature_list = [text_features(text, feature_words) for text in test_data_list]
    return test_feature_list  # 返回结果


def load_data_test(path):
    words_set = list()
    with open(path, 'r', encoding='utf-8') as f:
        for line in f.readlines():
            word = line.strip()
            if len(word) > 0:
                words_set.append(word)
    return words_set


"""
函数说明:新闻分类器
Parameters:
    train_feature_list - 训练集向量化的特征文本
    test_feature_list - 测试集向量化的特征文本
    train_class_list - 训练集分类标签
    test_class_list - 测试集分类标签
Returns:
    test_accuracy - 分类器精度
"""


def TextClassifier(test_feature_list, test_class_list):
    # 加载本地模型
    classifier = joblib.load('H:/PyWorkSpace/Naive_Bayes/mult.pkl')
    # 计算准确率（按照预测标签）
    test_accuracy = classifier.score(test_feature_list, test_class_list)
    # 输出预测的标签结果
    prediction_class_list = classifier.predict(test_feature_list)
    # print("预测的结果分类是：", prediction_class_list)
    # print("输入的分类是：", test_class_list)
    # print("预测准确率为", test_accuracy)
    return test_accuracy, prediction_class_list, test_class_list


def LoadTxt(path):
    file = os.path.exists(path)
    if file:
        with open(path, 'r', encoding='utf-8') as f:
            txt = f.read()
            txtList = txt.split("***分割线***")
        return txtList
    else:
        print("文件不存在")
        os._exit()


def getDataList(dataList):
    for i in range(len(dataList)):
        if (len(dataList[i]) > 0 and dataList[i] != "\n"):
            test_str_list.append(dataList[i])
            test_class_list.append(test_class0)
    return test_str_list, test_class_list


if __name__ == '__main__':
    # 需要过滤的连词，标点符号文件
    stopwords_file = 'H:/PyWorkSpace/Naive_Bayes/stopwords_cn.txt'
    # 读取文件
    stopwords_set = MakeWordsSet(stopwords_file)
    # 测算结果（可删除）
    test_accuracy_list = []
    # 测试样本数据
    test_str_list = []
    # 个人推算样本的类型
    test_class_list = []

    # test_0 = "2月28日下午，房天下控股、中指控股董事长莫天全携当代置业执行董事兼总裁张鹏，业之峰装饰集团董事长张钧，21世纪不动产中国区总裁兼CEO卢航，中经联盟秘书长、优铺网创始人陈云峰联合直播发声，畅谈疫情影响下房地产行业的发展。同时，这也是房天下控股董事长莫天全首次试水直播。" \
    #          "莫天全在直播中介绍称，疫情下的房地产及互联网行业迎来了发展的挑战和机遇。1月30号，房天下面向全行业免费开放在线直播开始直播卖房，2月6号面向产业精英推出直播大赛；2月26号，文旅地产界也开始举行330多个全国优秀文旅项目亮相。" \
    #          "莫天全表示，过去一个月的时间里面，房天下累计在线直播卖房超过了12万场，这还不包括二手房，观看人次累计过亿规模，近30万行业的从业人员加入了房天下的直播卖房大军。" \
    #          "当代置业执行董事兼董事长张鹏在直播中表示，自疫情发生以来，当代置业在三方面做了一番功夫，首先是捐款捐物，直接去承担一个企业的责任。公司第一时间已向武汉捐赠200万人民币，并且募集了一个千万规模的慈善基金。第二个就是，公司也积极响应国家号召，做好多项防疫举措。尤其是95个项目的6000位职员，与小区业主并肩作战。在房地产行业按下暂停键的同时，公司也在思考，如何通过提供更好的产品来服务社区。" \
    #          "张鹏表示，长期来看，企业应建立起自己的差异化竞争力，提升产品服务配套。" \
    #          "21世纪不动产中国区总裁兼CEO卢航在直播中表示，在疫情期间，21世纪不动产迅速推了一个21线上大学，把过去收费的课程剪出一个免费的精华版，帮大家去分析，假设疫情过后，第一步一动起来以后，到底哪些生意可能会先复苏，一分析特别简单，新房，第二就是租赁，第三就是一定要抓线上的看房，VR看房线上直播，就是线上的动作。" \
    #          "卢航认为，近段时间，经纪人也陆续有线上成交，为什么能做到这一点？其实这考验的是平时对线上客户的维护。各地中介门店也应在政府的指导下，规范的逐步复工。" \
    #          "中经联盟秘书长、优铺网创始人陈云峰认为疫情下，大家终于注意到这次物业公司的必要性。面对疫情，更多的优秀物业在默默的付出，我们也突然意识到物业对我们非常重要。而物业抢着在香港都轮着在上市，陈云峰认为对于物业公司是个重大的利好，大家都突然意识到这些大的商场可以不去，但是你离不开物业公司的保障，所以物业公司是受到了重视。" \
    #          "陈云峰还表示，当前市场下，商业及商业地产也面临较大的挑战，一些对商办项目的限制其实是不利于服务业发展、以及大众创业的，应该呼吁适当放松这方面的政策。" \
    #          "资料显示，疫情期间，房天下积极给出优惠，助力新房二手房市场在线成交。新房方面，房天下举办网上房交会，为开发商提供了一个很好地网上交易平台，更为购房者提供累计超过10亿的优惠。二手房方面，房天下免费开放了几万个网上门店，另有一个大优惠，为抗疫复产的二手房经纪公司、经纪人推出了翻倍赠送服务产品的大力度优惠政策。给经纪人提供支持，共同对抗疫情，推动市场复苏。"
    # test_1 = "大兴新里西斯莱公馆2居17000元可享96折" \
    #          "新里·西斯莱公馆 (论坛 相册 户型 样板间 点评 地图搜索)" \
    #          "项目位于大兴区黄村高米店收费站出口。11月27日开盘推新房源，贵宾楼5号楼。共有房源336套，所推户型有：130平米两居。180平米3居，260平米四居，312平米五居。起价17000元/平米，均价22000元/平米，全款96折，贷款98折，精装修，现前往售楼处可办卡，需要携带本人身份证，户口本等证件。" \
    #          "新里西斯莱公馆项目总占地面积约243亩，总建筑面积约45.8万平方米，规划建设集精装公寓、城市别墅、大型购物中心、餐饮酒吧街、俱乐部等于一体的大型城市综合体。" \
    #          ">>点击了解更多大兴区域新盘热盘信息" \
    #          "新里西斯莱公馆 周边配套设施齐全，华堂商场，物美大卖场，康庄公园，来子海鲜，神路居烧烤，面爱面，社区医院，应有尽有；交通便利，十多条公交线路经过项目。" \
    #          "以上信息仅供参考，最终以开发商公布为准。" \
    #          "点击查看更多打折优惠楼盘信息"
    #
    # test_2 = "<p></p><h1>第一章、操作系统引论</h1><p></p><h2>一、操作系统基础</h2><div>1、操作系统的定义：<span style=>操作系统（Operating System, OS）是指控制和管理整个计算机系统的硬件和软件资源，并合理的组织调度计算机的工作和资源的分配，以提供给用户和其他软件方便的接口和环境的程序集合。</span></div><div><span style2、操作系统的目标： <span style=;>方便</span>、<span style=有效</span>（提高系统资源的利用率，提高系统的吞吐量）、<span style=color:ff0000;>可扩充</span>（模块化，层次化，微内核）、</span><span style=color:ff0000;>开放</span></div><p></p><p></p><div>3、操作系统的作用：<span style=color:ff0000;>做用户和计算机硬件系统之间的接口</span>（命令，系统调用，图标-窗口），<span style=color:ff0000;>管理资源</span>（处理机，存储器，I/O设备，文件），<span style=color:ff0000;>资源的抽象</span></div><div><span style=color:005500;>4、操作系统发展动力：<span style=color:ff0000;>提高利用率</span>、<span style=color:ff0000;>更加方便</span>、<span style=color:ff0000;>应用需求/</span><span style=color:ff0000;>体系结构/</span></span><span style=color:ff0000;>硬件不断发展</span></div><h2>二 计算机发展史</h2><p></p><p></p><div>1、无操作系统：<span style=color:ff0000;>人工操作</span>（用户独占全机、CPU等待人工操作、内存长期空闲），<span style=color:ff0000;>脱机输入/输出（Off-Line I/O）</span>（装好纸带再上机，节约CPU空间时间，提高I/O速度）</div><div>2、单道批处理系统：<span style=color:ff0000;>有个监督程序将磁带上的作业调入计算机</span>，缺点是I/O太慢了，CPU空闲</div><div>3、多批道处理系统：</div><div><span style=color:333333;>描述：</span><span style=color:ff0000;>作业放入外存，形成队列，间隔执行</span>。</div><div>优点：资源利用率高，系统吞吐量大</div><div>缺点：平均周期长、无交互能力</div><div>存在问题：处理机，内存，I/O，文件，作业，接口</div><div>4、分时系统</div><div>描述：提供多个终端，多个用户使用，命令-反馈-命令第一章</div><div>优点：人机交互，多用户共享主机，</div><div>实际实现：<span style=color:ff0000;>终端有缓冲区暂存用户命令，作业直接进入内存，时间片轮转方式</span>（每个作业执行一个时间片，换下一个）</div><div>特征：多路性，独立性，及时性，交互性，</div><div>5、实时系统</div><div>描述： <span style=color:ff0000;>及时响应，规定时间内完成，控制所以实时任务协调一致的运行</span></div><div>类型：工业（武器）控制系统、信息查询系统、多媒体系统、嵌入式系统</div><div>实时任务的类型：周期性实时（指定周期）和非周期实时（开始截至和完成截止），硬实时（工业和武器，必须按时）和软实时（偶尔错，信息查询和多媒体）</div><div>和分时系统比较：多路性、独立性、及时性、交互性、可靠性</div><p></p><div>6、微机时代</div><div>a、单用户单任务：一个用户，执行一个任务8位机。CP/M，16位机MS-DOS</div><div>b、单用户多任务：一个用户，并发执行多个任务。Windows</div><div>c、多用户多任务：多个用户共享主机，并发执行多个任务。Unix、Solaris、Linux</div><h2>三、操作系统的基本特征</h2><p>1、并发</p><div>a、并发和并行宏观上一样</div><div>并发：两个或多个事件在<span style=color:ff0000;>同一时间间隔</span>内发生。单处理机系统，微观上交替运行</div><div>并行：两个或多个事件在<span style=color:ff0000;>同一时刻</span>发生。          多处理机系统，微观上同时运行</div><div>b、进入进程</div><div>进程：<span style=color:ff0000;>在系统中能独立运行并作为资源分配的基本单位，由机器指令、数据和堆栈组成，是独立运行的活动实体。</span></div><div>多个进程之间可以并发执行和交换信息</div><p>2、共享</p><div>概念：<span style=color:ff0000;>系统中资源可供<span style=color:3333ff;>内存</span>中多个<span style=color:3333ff;>并发执行的进程</span>共同使用</span>。</div><div>a<span style=color:ff0000;>.互斥共享方式</span></div><div><span style=color:ff0000;></span>描述：提出申请，资源空闲使用，不然等待</div><div>例子：打印机、磁带机等临界资源（同一时间只允许一个进程访问）</div><div>b<span style=color:ff0000;>.同时访问方式</span></div><div>描述：<span style=color:ff0000;>允许多个进程“同时”访问，微观还是交替进行</span></div><div>例子：磁盘设备</div><p><span style=color:ff0000;>共享以进程的并发为条件</span>，系统不能对共享有效管理，影响进程的并发</p><h4>3、虚拟</h4><p>概念：<span style=color:ff0000;>通过某种技术让一个物理实体变成若干逻辑上的对应物的功能</span>。（虚拟处理机，虚拟内存，虚拟外部设备，虚拟信道）（多重影分身之术）</p><div>a.时分复用技术</div><p>描述：<span style=color:ff0000;>利用某设备为一用户服务的空闲时间</span>，为其他用户服务，使设备得到最充分的利用</p><div>虚拟处理机技术：利用多道程序设计，每个程序至少一个进程，让多道程序并发</div><p>虚拟设备技术：将I/O设备虚拟为多台逻辑上的I/O设备</p><div>平均速度&lt;=1/N</div><div>b.空分复用技术</div><p></p><p></p><div>描述：<span style=color:ff0000;>利用存储器的空闲空间分区域</span>存放和运行其他的多道程序，以此提高内存的利用率</div><div>虚拟存储技术：通过分时复用内存，100M的程序运行在30M内存中，各部分分时进入内存运行</div><p></p><p></p><div>空间占用&lt;=1/N</div><div>4、异步</div><p></p><p></p><div>描述：就是为了让进程更好的并发，系统会分配进程的执行顺序（走走停停）</div><div><br /></div><p></p><h2>四、操作系统的主要功能</h2><p></p><p></p><div>处理机管理，存储器管理，I/O设备管理，文件管理，提供接口</div><h4>1、处理机管理</h4><p>a.<span style=color:ff0000;>进程控制</span>：为作业创建进程、撤销已结束的进程，控制进程在运行中的状态转换</p><div>b<span style=color:ff0000;>.进程同步</span>：为多个进程（含线程）的运行进行协调（进程互斥方式，进程同步方式）</div><p>c.<span style=color:ff0000;>进程通信</span>：实现相互合作进程之间的信息交换</p><p>d.<span style=color:ff0000;>调度</span>：作业调度（从后备队列选出作业，创建进程，插入就绪队列）和进程调度（从进程的就绪队列选出进程分配处理机，投入执行）</p><h4>2、存储器管理</h4><p>内存分配和回收、内存保护、地址映射、内存扩充</p><div>a.<span style=color:ff0000;>内存分配</span>：</div><p></p><p>任务：<span style=color:ff0000;>分配空间，减少碎片内存，追加内存空间</span></p><div>实现：静态分配，装入内存时确定，不允许追加，不允许移动；动态分配，装入内存时确定，可以追加和移动</div><p></p><p>b.<span style=color:ff0000;>内存保护</span></p><div>任务：在自己空间运行，互不干扰；不允许用户程序访问操作系统的程序和数据，也不允许用户程序转移到非共享的其他用户程序中执行</div><p></p><p>内存保护机制：<span style=color:333333;>设置两个<span style=color:ff0000;>界限</span>寄存器（上界和下界），对每条指令要访问的<span style=color:ff0000;>地址</span>进行检查，如果越界就停止该程序</span></p><div>c.<span style=color:ff0000;>地址映射</span></div><p></p><p></p><div>任务：将地址空间中的<span style=color:ff0000;>逻辑地址</span>转为内存空间的<span style=color:ff0000;>物理地址</span>。在硬件的支持下完成</div><div>d<span style=color:ff0000;>.内存扩充</span></div><p>任务：用<span style=color:ff0000;>虚拟存储技</span>术，从逻辑上扩充内存容量</p><p>内存扩充机制：</p><div>请求调入（装入<span style=color:ff0000;>部分</span>用户程序和数据就能启动程序，后续再将其他部分调入内存），</div><div>置换（若内存不够，将暂时不用的程序和数据<span style=color:ff0000;>调至硬盘</span>）</div><p></p><p>3、设备管理</p><div>任务1：完成用户进程提出的I/O请求，为用户进程分配所需的I/O设备，并完成指定的I/O操作</div><p>任务2：提高CPU和I/O设备的利用率，提高I/O速度，方便用户使用I/O设备</p><p>缓冲管理，设备分配，设备处理，虚拟设备</p><div>a.缓冲管理</div><p>作用：有效地<span style=color:ff0000;>缓和</span>CPU和I/O设备速度不匹配的<span style=color:ff0000;>矛盾</span>，<span style=color:ff0000;>提高</span>了CPU的<span style=color:ff0000;>利用率</span>，进而<span style=color:ff0000;>提高系统吞吐量</span></p><div>手段：<span style=color:ff0000;>在内存中设置缓冲区，增加缓冲区容量</span></div><p>缓冲区机制：<span style=color:ff0000;>单缓冲</span>，<span style=color:ff0000;>双缓冲</span>（能实现双向同时传送数据），<span style=color:ff0000;>公用缓冲池</span>（能供多个设备同时使用）</p><div>b.设备分配</div><p></p><p>任务：根据用户进程的I/O请求。系统现有资源情况以及按照某种设备分配策略，为之分配所需的设备</p><div>手段：系统中设置<span style=color:ff0000;>设备控制表</span>、<span style=color:ff0000;>控制器控制表</span>等数据结构，用以记录设备及控制器等标识符和状态。根据这些表就能知道指定设备是否可用，是否忙碌。<span style=color:3333ff;>针对不同的设备类型采用不同的设备分配方式</span>。对<span style=color:ff0000;>独占设备的分配</span>还应考虑分配出去后系统<span style=color:ff0000;>是否安全</span>。</div><p>c.设备处理</p><div>任务：<span style=color:ff0000;>用于实现CPU和设备控制器之间的通信</span>（CPU向设备控制器发出I/O命令，要求他完成指定的I/O操作；CPU接收从控制器发来的中断请求，并给予迅速的响应和处理）</div><p>处理过程：检查I/O请求的合法性、设备状态是否空闲，读取有关参数、设置设备的工作方式，然后向设备控制器发出I/O命令，启动I/O设备完成指定的I/O操作。</p><div>4、文件管理</div><p>任务：对用户文件和系统文件进行管理以便用户使用，并保证文件的安全性</p><p>文件存储空间的管理，目录管理，文件的读写管理，文件的共享和保护</p><div>a.文件存储空间的管理</div><p>背景：多用户环境，用户自己管理文件存储，困难且低效</p><div>任务1：为每个文件<span style=color:ff0000;>分配额外的外存空间</span>，<span style=color:ff0000;>提高外存利用率</span>，进而提高存取速度</div><p>任务2：设置相应的数据结构，<span style=color:ff0000;>记录文件存储空间使用情况</span>，以供分配时参考</p><p>任务3：<span style=color:ff0000;>分配和回收</span>存储空间</p><div>b.目录管理</div><p></p><p>为了使用户能方便地在外存上找到自己所需的文件，通常由系统为<span style=color:ff0000;>每个文件建立一个目录项</span>。目录项包括<span style=color:3333ff;>文件名</span>、<span style=color:3333ff;>文件属性</span>、文件在磁盘上的<span style=color:3333ff;>物理位置</span>等。由<span style=color:ff0000;>若干个目录项又可构成一个目录文件</span></p><div>任务1：为每个文件建立一个目录项，并对众多的目录项加以有效的组织，以实现方便的<span style=color:cc0000;>按名存取</span>（即用户只需提供文件名，就能对该文件进行存取）</div><p>任务2：实现<span style=color:ff0000;>文件共享</span>，只需在外存保留一份该共享文件的副本</p><div>任务3：提供快速的<span style=color:ff0000;>目录查询</span>手段，以提高对文件的检索速度</div><p>c.文件读写管理</p><div>任务：根据用户的请求，从<span style=color:ff0000;>外存读取数据</span>或将<span style=color:ff0000;>数据写入外存</span></div><p>过程：根据文件名检索文件目录获得外存地址，利用读写指针对文件进行读写，然后修改读写指针，等待下一次读写。读写不会同时进行，所以共用一个指针</p><div>d.文件保护</div><p>1.防止<span style=color:ffcc00;>未经核准</span>的用户存取文件</p><div>2.防止<span style=color:ffcc00;>冒名顶替</span>存取文件</div><div>3.防止<span style=color:ffcc00;>以不正确的方式</span>使用文件</div><p></p><p></p><div><br /></div><div>5、操作系统与用户间的接口</div><div>a.用户接口</div><p>描述：方便用户直接或间接地控制自己的作业</p><div>1、联机用户接口：由<span style=color:3333ff;>一组键盘操作命令及命令解释程序</span>组成。<span style=color:ff0000;>用户通过先后键入不同的命令来实现对作业的控制</span>。</div><div>2、脱机用户接口：为<span style=color:3333ff;>批处理作业</span>的用户提供，由<span style=color:3333ff;>作业控制语言JCL</span>组成。用户把<span style=color:ff0000;>对作业进行控制和干预的命令写在作业说明书</span>中，和<span style=color:ff0000;>作业一起</span>提供给系统。<span style=color:ff0000;>调用命令解释程序按照说明书执行，遇到作业结束语句停止</span>。</div><p>3、图形用户接口：<span style=color:ff0000;>把各项功能、应用等封装成图标</span>，选种图标即可执行相应操作。</p><div>b.程序接口：</div><p>描述：为用户程序在<span style=color:ff0000;>执行中访问系统资源</span>而设置，是<span style=color:ff0000;>用户取得操作系统服务</span>的唯一途径。它是由<span style=color:3333ff;>一组系统调用</span>组成，每一个系统调用都是一个能完成特定功能的子程序，每当应用程序要求OS提供某种功能时，便调用具有相应功能的系统调用</p><h1>第二章 进程的描述和控制</h1><p></p><p></p><div>一、进程的基本概念</div><div>程序有顺序执行和并发执行</div><div>顺序执行：单道批处理系统，用户独占资源</div>并发执行：多道程序系统，多道程序并发执行，共享资源，引入进程的概念<p>1、程序的顺序执行及其特征</p><p> 仅当前一操作(程序段)执行完后，才能执行后继操作。S1:     a∶=x+y; S2:     b∶=a-5; S3:     c∶=b+1;<br /></p><div>在进行计算时，总须先输入用户的程序和数据，然后进行计算，最后才能打印计算"
    test_class0 = ''
    test_class1 = ''
    test_class2 = '娱乐'

    # for i in range(1, 1, 3):
    # test_str_list.append(sys.argv[1])
    # test_class_list.append(test_class0)
    # data_from_txt = LoadTxt("H:/JavaCode/ScriptSpider-master/text.txt")
    data_from_txt = LoadTxt(sys.argv[1])

    print(data_from_txt)
    test_str_list, test_class_list = getDataList(data_from_txt)

    # 中文文本处理,把数据用jieba分词好后 和标签一一对应
    test_data_list, test_class_list = TextProcessing(test_str_list, test_class_list)
    # 加载文本特征，这里的特征是已经训练好的（5000词，2000维度，0.1平滑）
    feature_words = load_data_test('H:/PyWorkSpace/Naive_Bayes/words_test.txt')
    # 根据文本特征把待测数据文本向量化
    test_feature_list = TextFeatures(test_data_list, feature_words)
    # 新闻分类器，通过读取模型对数据进行分类预测，输出结果
    test_accuracy, prediction_class_list, test_class_list = TextClassifier(test_feature_list, test_class_list)
    print(prediction_class_list)
    # test_accuracy_list.append(test_accuracy)
    # ave = lambda c: sum(c) / len(c)
    # print(ave(test_accuracy_list))
