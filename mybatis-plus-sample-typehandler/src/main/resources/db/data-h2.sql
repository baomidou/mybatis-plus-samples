DELETE FROM sys_user;

INSERT INTO sys_user (id, name, age, email, wallets, other_info) VALUES
(1, 'Jone', 18, 'test1@baomidou.com', '[{
    "name": "支付宝钱包",
    "currencyList": [{
        "type": "USD",
        "amount": 999.19
    },{
        "type": "RMB",
        "amount": 1000.19
    }]
},{
    "name": "微信钱包",
    "currencyList": [{
        "type": "USD",
        "amount": 888.18
    },{
        "type": "RMB",
        "amount": 1000.18
    }]
}]', '{
        "sex": "男",
        "city": "南昌"
}'),
(2, 'Jack', 20, 'test2@baomidou.com', '[{
    "name": "银联钱包",
    "currencyList": [{
        "type": "USD",
        "amount": 888.18
    },{
        "type": "RMB",
        "amount": 1000.18
    }]
}]', '{
        "sex": "男",
        "city": "青岛"
}');