///**
// * 存在状态机做串联时，统一的事务处理，将状态机实例持久化也囊括在统一的事务中
// */
//public interface StateMachineSendEventManager {
//
//    /**
//     * 发送状态机event，调用bizManagerImpl中具体实现，同时处理状态机持久化
//     * <p>
//     * 用于订单的状态变更
//     *
//     * @param request
//     * @param operationTypeEnum
//     * @param eventEnum
//     * @return
//     * @throws BusinessException
//     */
//    OrderBaseResponse sendStatusChangeEvent(BizOrderStatusRequest request,
//                                            BizOrderOperationTypeEnum operationTypeEnum,
//                                            BizOrderStatusChangeEventEnum eventEnum) throws Exception;
//
//
//    /**
//     * 同上，不过是用于订单创建场景
//     *
//     * @param request
//     * @param operationTypeEnum
//     * @param eventEnum
//     * @return
//     * @throws Exception
//     */
//    BizOrderCreateResponse sendOrderCreateEvent(BizOrderCreateRequest request,
//                                                BizOrderOperationTypeEnum operationTypeEnum,
//                                                BizOrderStatusChangeEventEnum eventEnum) throws Exception;
//
//}